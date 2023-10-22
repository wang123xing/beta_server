package cn.beta.platform.service.impl;

import cn.beta.platform.constant.CacheConstant;
import cn.beta.platform.constant.CommonConstant;
import cn.beta.platform.entity.User;
import cn.beta.platform.entity.dto.LoginUserDTO;
import cn.beta.platform.enums.DeletedStatusEnum;
import cn.beta.platform.enums.LoginTypeEnum;
import cn.beta.platform.mapper.UserMapper;
import cn.beta.platform.service.UserService;
import cn.beta.platform.utils.ConvertUtils;
import cn.beta.platform.utils.JwtUtil;
import cn.beta.platform.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import cn.beta.platform.exception.AppException;
import cn.beta.platform.enums.ResultEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static cn.beta.platform.constant.CommonConstant.X_ACCESS_TOKEN;
import static cn.beta.platform.enums.ResultEnum.TOKEN_LOGOUT;
import static cn.beta.platform.enums.ResultEnum.TOKEN_NOT_FOUNT;

/**
* <p>
* 运营账户 服务实现类
* </p>
*
* @author beta
* @since 2023-10-22
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    @Lazy
    private RedisUtil redisUtil;
    @Override
    public Page<User> listUsersByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询user: page = {} pageSize = {} factor = {}",page,pageSize,factor);
        QueryWrapper<User> queryWrapper =  new QueryWrapper<User>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<User> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询user完毕: 结果数 = {} ",result.getRecords().size());
        return result;
    }

    @Override
    public User getUserById(Long id) {
        log.info("正在查询user中id为{}的数据",id);
        User user = super.getById(id);
        log.info("查询id为{}的user{}",id,(null == user?"无结果":"成功"));
        return user;
    }

    @Override
    public Long insertUser(User user) {
        log.info("正在插入user");
        if (super.save(user)) {
            log.info("插入user成功,id为{}",user.getId());
            return user.getId();
        } else {
            log.error("插入user失败");
            throw new AppException(ResultEnum.SYS_ERROR,"插入失败");
        }
    }

    @Override
    public Long deleteUserById(Long id) {
        log.info("正在删除id为{}的user",id);
        if (super.removeById(id)) {
            log.info("删除id为{}的user成功",id);
            return id;
        } else {
            log.error("删除id为{}的user失败",id);
            throw new AppException(ResultEnum.SYS_ERROR,"删除失败[id=" + id + "]");
        }
    }

    @Override
    public Long updateUser(User user) {
        log.info("正在更新id为{}的user",user.getId());
        if (super.updateById(user)) {
            log.info("更新d为{}的user成功",user.getId());
            return user.getId();
        } else {
            log.error("更新id为{}的user失败",user.getId());
            throw new AppException(ResultEnum.SYS_ERROR,"更新失败[id=" + user.getId() + "]");
        }
    }

    /**
     * 登录
     *
     * @param userName
     * @param passWord
     * @param loginTypeEnum
     * @return
     */
    @Override
    public LoginUserDTO login(String userName, String passWord, LoginTypeEnum loginTypeEnum) {
        switch (loginTypeEnum){
            case PLATFORM:
                LambdaQueryWrapper<User> queryWrapper =  new LambdaQueryWrapper<User>();
                queryWrapper.eq(User::getUsername,userName);
                queryWrapper.eq(User::getPassword,passWord);
                LoginUserDTO user = Optional.ofNullable(getUserByNameAndPass(userName,passWord)).orElseThrow(()-> new AppException(ResultEnum.USER_NOT_FOUNT));
                // 生成token
                String token = JwtUtil.sign(user.getUsername(), user.getPassword());
                // 设置token缓存有效时间
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 );
                user.setToken(token);
                return user;
            case COMMISSION_AGENT:
                throw new AppException(ResultEnum.SYS_ERROR,"代理商错误");
        }
        throw new AppException(ResultEnum.SYS_ERROR,"不存在的的登录角色");
    }

    @Override
    public LoginUserDTO getUserByLoginName(String jwtMessage) {
        LambdaQueryWrapper<User> queryWrapper =  new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUsername,jwtMessage);
        queryWrapper.eq(User::getIsDel, DeletedStatusEnum.DELETED_NO.getValue());
        LoginUserDTO loginUserDTO = Optional.ofNullable(baseMapper.selectOne(queryWrapper))
                .map(mallUser -> {
                    LoginUserDTO loginUser = new LoginUserDTO();
                    BeanUtils.copyProperties(mallUser, loginUser);
                    return loginUser;
                })
                .orElse(null);
        return loginUserDTO;
    }

    @Override
    public boolean updateToken(String token, Long expireTime) {
        String jwtMessage = JwtUtil.getLoginName(token);
        if (jwtMessage == null) {
            throw new AppException(TOKEN_NOT_FOUNT);
        }
        LoginUserDTO loginUser = getUserByLoginName(jwtMessage);
        String cacheToken = String.valueOf(redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + token));
        log.info("Redis cache Token : {}",cacheToken);
        if (ConvertUtils.isNotEmpty(cacheToken)) {
            // 校验token有效性
            String newAuthorization = JwtUtil.sign(loginUser.getUsername(), loginUser.getPassword());
            // 设置超时时间
            redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, newAuthorization);
            redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 );
            log.info("——————————用户在线更新token保证不掉线—————————jwtTokenRefresh——————— " + token);
            return true;
        }
        return false;
    }

    @Override
    public String logout(HttpServletRequest request) {
        //退出
        String token = request.getHeader(X_ACCESS_TOKEN);
        if (ConvertUtils.isEmpty(token)) {
            return TOKEN_NOT_FOUNT.getMessage();
        }
        String mobile = JwtUtil.getLoginName(token);
        //清空用户登录Token缓存
        redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
        //清空用户登录Shiro权限缓存
        redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + mobile);
        //调用shiro的logout
        SecurityUtils.getSubject().logout();
        return TOKEN_LOGOUT.getMessage();
    }

    private LoginUserDTO getUserByNameAndPass(String userName, String passWord) {
        LambdaQueryWrapper<User> queryWrapper =  new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUsername,userName);
        queryWrapper.eq(User::getPassword,passWord);
        queryWrapper.eq(User::getIsDel, DeletedStatusEnum.DELETED_NO.getValue());
        LoginUserDTO loginUserDTO = Optional.ofNullable(baseMapper.selectOne(queryWrapper))
                .map(mallUser -> {
                    LoginUserDTO loginUser = new LoginUserDTO();
                    BeanUtils.copyProperties(mallUser, loginUser);
                    return loginUser;
                })
                .orElse(null);
        return loginUserDTO;
    }

}
