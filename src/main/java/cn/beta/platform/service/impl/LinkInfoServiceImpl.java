package cn.beta.platform.service.impl;

import cn.beta.platform.entity.LinkInfo;
import cn.beta.platform.mapper.LinkInfoMapper;
import cn.beta.platform.service.LinkInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import cn.beta.platform.exception.AppException;
import cn.beta.platform.enums.ResultEnum;

/**
* <p>
* 推广链接 服务实现类
* </p>
*
* @author beta
* @since 2023-10-22
*/
@Slf4j
@Service
public class LinkInfoServiceImpl extends ServiceImpl<LinkInfoMapper, LinkInfo> implements LinkInfoService {

    @Override
    public Page<LinkInfo> listLinkInfosByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询linkInfo: page = {} pageSize = {} factor = {}",page,pageSize,factor);
        QueryWrapper<LinkInfo> queryWrapper =  new QueryWrapper<LinkInfo>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<LinkInfo> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询linkInfo完毕: 结果数 = {} ",result.getRecords().size());
        return result;
    }

    @Override
    public LinkInfo getLinkInfoById(Long id) {
        log.info("正在查询linkInfo中id为{}的数据",id);
        LinkInfo linkInfo = super.getById(id);
        log.info("查询id为{}的linkInfo{}",id,(null == linkInfo?"无结果":"成功"));
        return linkInfo;
    }

    @Override
    public Long insertLinkInfo(LinkInfo linkInfo) {
        log.info("正在插入linkInfo");
        if (super.save(linkInfo)) {
            log.info("插入linkInfo成功,id为{}",linkInfo.getId());
            return linkInfo.getId();
        } else {
            log.error("插入linkInfo失败");
            throw new AppException(ResultEnum.SYS_ERROR,"插入失败");
        }
    }

    @Override
    public Long deleteLinkInfoById(Long id) {
        log.info("正在删除id为{}的linkInfo",id);
        if (super.removeById(id)) {
            log.info("删除id为{}的linkInfo成功",id);
            return id;
        } else {
            log.error("删除id为{}的linkInfo失败",id);
            throw new AppException(ResultEnum.SYS_ERROR,"删除失败[id=" + id + "]");
        }
    }

    @Override
    public Long updateLinkInfo(LinkInfo linkInfo) {
        log.info("正在更新id为{}的linkInfo",linkInfo.getId());
        if (super.updateById(linkInfo)) {
            log.info("更新d为{}的linkInfo成功",linkInfo.getId());
            return linkInfo.getId();
        } else {
            log.error("更新id为{}的linkInfo失败",linkInfo.getId());
            throw new AppException(ResultEnum.SYS_ERROR,"更新失败[id=" + linkInfo.getId() + "]");
        }
    }

}
