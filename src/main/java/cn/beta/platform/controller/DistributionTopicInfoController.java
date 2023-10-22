package cn.beta.platform.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import cn.beta.platform.entity.dto.ResultBean;
import cn.beta.platform.service.DistributionTopicInfoService;
import cn.beta.platform.entity.DistributionTopicInfo;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;


/**
 * <p>
 * 分销基础专题信息 前端控制器
 * </p>
 *
 * @author beta
 * @since 2023-10-22
 * @version v1.0
 */
@RestController
@RequestMapping("/platform/api/v1/distribution-topic-info")
public class DistributionTopicInfoController {

    @Resource
    private DistributionTopicInfoService distributionTopicInfoService;

    /**
    * 查询分页数据
    */
    @RequestMapping(method = RequestMethod.GET)
    public ResultBean<?> listByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                    @RequestParam(name = "factor", defaultValue = "") String factor) {
        return new ResultBean<>(distributionTopicInfoService.listDistributionTopicInfosByPage(page, pageSize,factor));
    }


    /**
    * 根据id查询
    */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResultBean<?> getById(@PathVariable("id") Long id) {
        return new ResultBean<>(distributionTopicInfoService.getDistributionTopicInfoById(id));
    }

    /**
    * 新增
    */
    @RequestMapping(method = RequestMethod.POST)
    public ResultBean<?> insert(@RequestBody DistributionTopicInfo distributionTopicInfo) {
        return new ResultBean<>(distributionTopicInfoService.insertDistributionTopicInfo(distributionTopicInfo));
    }

    /**
    * 删除
    */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResultBean<?> deleteById(@PathVariable("id") Long id) {
        return new ResultBean<>(distributionTopicInfoService.deleteDistributionTopicInfoById(id));
    }

    /**
    * 修改
    */
    @RequestMapping(method = RequestMethod.PUT)
    public ResultBean<?> updateById(@RequestBody DistributionTopicInfo distributionTopicInfo) {
        return new ResultBean<>(distributionTopicInfoService.updateDistributionTopicInfo(distributionTopicInfo));
    }
}
