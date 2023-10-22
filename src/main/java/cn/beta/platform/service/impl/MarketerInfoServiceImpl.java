package cn.beta.platform.service.impl;

import cn.beta.platform.entity.MarketerInfo;
import cn.beta.platform.mapper.MarketerInfoMapper;
import cn.beta.platform.service.MarketerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import cn.beta.platform.exception.AppException;
import cn.beta.platform.enums.ResultEnum;

/**
* <p>
* 营销商信息表 服务实现类
* </p>
*
* @author beta
* @since 2023-10-22
*/
@Slf4j
@Service
public class MarketerInfoServiceImpl extends ServiceImpl<MarketerInfoMapper, MarketerInfo> implements MarketerInfoService {

    @Override
    public Page<MarketerInfo> listMarketerInfosByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询marketerInfo: page = {} pageSize = {} factor = {}",page,pageSize,factor);
        QueryWrapper<MarketerInfo> queryWrapper =  new QueryWrapper<MarketerInfo>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<MarketerInfo> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询marketerInfo完毕: 结果数 = {} ",result.getRecords().size());
        return result;
    }

    @Override
    public MarketerInfo getMarketerInfoById(Long id) {
        log.info("正在查询marketerInfo中id为{}的数据",id);
        MarketerInfo marketerInfo = super.getById(id);
        log.info("查询id为{}的marketerInfo{}",id,(null == marketerInfo?"无结果":"成功"));
        return marketerInfo;
    }

    @Override
    public Long insertMarketerInfo(MarketerInfo marketerInfo) {
        log.info("正在插入marketerInfo");
        if (super.save(marketerInfo)) {
            log.info("插入marketerInfo成功,id为{}",marketerInfo.getId());
            return marketerInfo.getId();
        } else {
            log.error("插入marketerInfo失败");
            throw new AppException(ResultEnum.SYS_ERROR,"插入失败");
        }
    }

    @Override
    public Long deleteMarketerInfoById(Long id) {
        log.info("正在删除id为{}的marketerInfo",id);
        if (super.removeById(id)) {
            log.info("删除id为{}的marketerInfo成功",id);
            return id;
        } else {
            log.error("删除id为{}的marketerInfo失败",id);
            throw new AppException(ResultEnum.SYS_ERROR,"删除失败[id=" + id + "]");
        }
    }

    @Override
    public Long updateMarketerInfo(MarketerInfo marketerInfo) {
        log.info("正在更新id为{}的marketerInfo",marketerInfo.getId());
        if (super.updateById(marketerInfo)) {
            log.info("更新d为{}的marketerInfo成功",marketerInfo.getId());
            return marketerInfo.getId();
        } else {
            log.error("更新id为{}的marketerInfo失败",marketerInfo.getId());
            throw new AppException(ResultEnum.SYS_ERROR,"更新失败[id=" + marketerInfo.getId() + "]");
        }
    }

}
