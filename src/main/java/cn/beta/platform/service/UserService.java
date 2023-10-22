package cn.beta.platform.service;

import cn.beta.platform.entity.User;
import cn.beta.platform.entity.dto.LoginUserDTO;
import cn.beta.platform.enums.LoginTypeEnum;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletRequest;

/**
* <p>
* 运营账户 服务类
* </p>
*
* @author beta
* @since 2023-10-22
*/
public interface UserService {

    /**
    * 分页查询User
    *
    * @param page     当前页数
    * @param pageSize 页的大小
    * @param factor  搜索关键词
    * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
    * @author beta
    * @since 2023-10-22
    */
    Page<User> listUsersByPage(int page, int pageSize, String factor);

    /**
    * 根据id查询User
    *
    * @param id 需要查询的User的id
    * @return 返回对应id的User对象
    * @author beta
    * @since 2023-10-22
    */
    User getUserById(Long id);

    /**
    * 插入User
    *
    * @param user 需要插入的User对象
    * @return 返回插入成功之后User对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long insertUser(User user);

    /**
    * 根据id删除User
    *
    * @param id 需要删除的User对象的id
    * @return 返回被删除的User对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long deleteUserById(Long id);

    /**
    * 根据id更新User
    *
    * @param user 需要更新的User对象
    * @return 返回被更新的User对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long updateUser(User user);

    /**
     * 登录
     * @param userName
     * @param passWord
     * @param loginTypeEnum
     * @return
     */
    LoginUserDTO login(String userName, String passWord, LoginTypeEnum loginTypeEnum);

    LoginUserDTO getUserByLoginName(String jwtMessage);

    boolean updateToken(String token, Long expireTime);

    String logout(HttpServletRequest request);
}
