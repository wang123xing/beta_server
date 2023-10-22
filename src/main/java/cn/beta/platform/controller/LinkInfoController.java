package cn.beta.platform.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import cn.beta.platform.entity.dto.ResultBean;
import cn.beta.platform.service.LinkInfoService;
import cn.beta.platform.entity.LinkInfo;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;


/**
 * <p>
 * 推广链接 前端控制器
 * </p>
 *
 * @author beta
 * @since 2023-10-22
 * @version v1.0
 */
@RestController
@RequestMapping("/platform/api/v1/link-info")
public class LinkInfoController {

    @Resource
    private LinkInfoService linkInfoService;

    /**
    * 查询分页数据
    */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(name = "factor", defaultValue = "") String factor) {
        return new ResultBean<>(linkInfoService.listLinkInfosByPage(page, pageSize,factor));
    }


    /**
    * 根据id查询
    */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Long id) {
        return new ResultBean<>(linkInfoService.getLinkInfoById(id));
    }

    /**
    * 新增
    */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody LinkInfo linkInfo) {
        return new ResultBean<>(linkInfoService.insertLinkInfo(linkInfo));
    }

    /**
    * 删除
    */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Long id) {
        return new ResultBean<>(linkInfoService.deleteLinkInfoById(id));
    }

    /**
    * 修改
    */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody LinkInfo linkInfo) {
        return new ResultBean<>(linkInfoService.updateLinkInfo(linkInfo));
    }
}
