package cn.beta.platform.controller;


import cn.beta.platform.enums.LoginTypeEnum;
import cn.beta.platform.enums.ResultEnum;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import cn.beta.platform.entity.dto.ResultBean;
import cn.beta.platform.service.UserService;
import cn.beta.platform.entity.User;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 运营账户 前端控制器
 * </p>
 *
 * @author beta
 * @since 2023-10-22
 * @version v1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;



    /**
     * 注册
     */
    @PostMapping("/Register")
    @ApiOperation(value = "注册")
    public ResultBean<?> insert(@RequestBody User user) {
        return new ResultBean<>(userService.insertUser(user));
    }
    @PostMapping("/Login")
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "账号", example = "13947653456"),
            @ApiImplicitParam(name = "passWord", value = "密码", example = "1231231"),
            @ApiImplicitParam(name = "loginType", value = "登录类型：1代表运营，2代表代理商", example = "1",defaultValue = "1"),
    })
    public ResultBean<?> login(@RequestParam String loginName,
                               @RequestParam String passWord,
                               @RequestParam(value = "loginType",required = false,defaultValue = "1") LoginTypeEnum loginType) {
        return new ResultBean<>(userService.login(loginName, passWord,loginType));

    }

    @GetMapping("/Logout")
    @ApiOperation(value = "退出登录")
    public ResultBean<?> logout(HttpServletRequest request) {
        return new ResultBean<>(userService.logout(request));
    }
    @GetMapping("/updateToken")
    @ApiOperation(value = "刷新在线状态")
    public ResultBean<String> updateToken(@RequestParam String token, Long expireTime) {
        return userService.updateToken(token, expireTime)?ResultBean.success(ResultEnum.SUCCESS.getMessage()):ResultBean.error(ResultEnum.FAIL);
    }

    /**
    * 查询分页数据
    */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(name = "factor", defaultValue = "") String factor) {
        return new ResultBean<>(userService.listUsersByPage(page, pageSize,factor));
    }
    /**
     * 修改
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody User user) {
        return new ResultBean<>(userService.updateUser(user));
    }

    /**
     * 删除
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Long id) {
        return new ResultBean<>(userService.deleteUserById(id));
    }

    /**
    * 根据id查询
    */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Long id) {
        return new ResultBean<>(userService.getUserById(id));
    }

}
