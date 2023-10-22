package cn.beta.platform.service.impl;

import cn.beta.platform.entity.DistributionTopicInfo;
import cn.beta.platform.mapper.DistributionTopicInfoMapper;
import cn.beta.platform.service.DistributionTopicInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import cn.beta.platform.exception.AppException;
import cn.beta.platform.enums.ResultEnum;

/**
* <p>
* 分销基础专题信息 服务实现类
* </p>
*
* @author beta
* @since 2023-10-22
*/
@Slf4j
@Service
public class DistributionTopicInfoServiceImpl extends ServiceImpl<DistributionTopicInfoMapper, DistributionTopicInfo> implements DistributionTopicInfoService {

    @Override
    public Page<DistributionTopicInfo> listDistributionTopicInfosByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询distributionTopicInfo: page = {} pageSize = {} factor = {}",page,pageSize,factor);
        QueryWrapper<DistributionTopicInfo> queryWrapper =  new QueryWrapper<DistributionTopicInfo>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<DistributionTopicInfo> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询distributionTopicInfo完毕: 结果数 = {} ",result.getRecords().size());
        return result;
    }

    @Override
    public DistributionTopicInfo getDistributionTopicInfoById(Long id) {
        log.info("正在查询distributionTopicInfo中id为{}的数据",id);
        DistributionTopicInfo distributionTopicInfo = super.getById(id);
        log.info("查询id为{}的distributionTopicInfo{}",id,(null == distributionTopicInfo?"无结果":"成功"));
        return distributionTopicInfo;
    }

    @Override
    public Long insertDistributionTopicInfo(DistributionTopicInfo distributionTopicInfo) {
        log.info("正在插入distributionTopicInfo");
        if (super.save(distributionTopicInfo)) {
            log.info("插入distributionTopicInfo成功,id为{}",distributionTopicInfo.getId());
            return distributionTopicInfo.getId();
        } else {
            log.error("插入distributionTopicInfo失败");
            throw new AppException(ResultEnum.SYS_ERROR,"插入失败");
        }
    }

    @Override
    public Long deleteDistributionTopicInfoById(Long id) {
        log.info("正在删除id为{}的distributionTopicInfo",id);
        if (super.removeById(id)) {
            log.info("删除id为{}的distributionTopicInfo成功",id);
            return id;
        } else {
            log.error("删除id为{}的distributionTopicInfo失败",id);
            throw new AppException(ResultEnum.SYS_ERROR,"删除失败[id=" + id + "]");
        }
    }

    @Override
    public Long updateDistributionTopicInfo(DistributionTopicInfo distributionTopicInfo) {
        log.info("正在更新id为{}的distributionTopicInfo",distributionTopicInfo.getId());
        if (super.updateById(distributionTopicInfo)) {
            log.info("更新d为{}的distributionTopicInfo成功",distributionTopicInfo.getId());
            return distributionTopicInfo.getId();
        } else {
            log.error("更新id为{}的distributionTopicInfo失败",distributionTopicInfo.getId());
            throw new AppException(ResultEnum.SYS_ERROR,"更新失败[id=" + distributionTopicInfo.getId() + "]");
        }
    }

}
