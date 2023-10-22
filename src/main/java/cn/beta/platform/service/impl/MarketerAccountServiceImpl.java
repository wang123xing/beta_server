package cn.beta.platform.service.impl;

import cn.beta.platform.entity.MarketerAccount;
import cn.beta.platform.mapper.MarketerAccountMapper;
import cn.beta.platform.service.MarketerAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import cn.beta.platform.exception.AppException;
import cn.beta.platform.enums.ResultEnum;

/**
* <p>
* 营销商账户表 服务实现类
* </p>
*
* @author beta
* @since 2023-10-22
*/
@Slf4j
@Service
public class MarketerAccountServiceImpl extends ServiceImpl<MarketerAccountMapper, MarketerAccount> implements MarketerAccountService {

    @Override
    public Page<MarketerAccount> listMarketerAccountsByPage(int page, int pageSize, String factor) {
        log.info("正在执行分页查询marketerAccount: page = {} pageSize = {} factor = {}",page,pageSize,factor);
        QueryWrapper<MarketerAccount> queryWrapper =  new QueryWrapper<MarketerAccount>().like("", factor);
        //TODO 这里需要自定义用于匹配的字段,并把wrapper传入下面的page方法
        Page<MarketerAccount> result = super.page(new Page<>(page, pageSize));
        log.info("分页查询marketerAccount完毕: 结果数 = {} ",result.getRecords().size());
        return result;
    }

    @Override
    public MarketerAccount getMarketerAccountById(Long id) {
        log.info("正在查询marketerAccount中id为{}的数据",id);
        MarketerAccount marketerAccount = super.getById(id);
        log.info("查询id为{}的marketerAccount{}",id,(null == marketerAccount?"无结果":"成功"));
        return marketerAccount;
    }

    @Override
    public Long insertMarketerAccount(MarketerAccount marketerAccount) {
        log.info("正在插入marketerAccount");
        if (super.save(marketerAccount)) {
            log.info("插入marketerAccount成功,id为{}",marketerAccount.getId());
            return marketerAccount.getId();
        } else {
            log.error("插入marketerAccount失败");
            throw new AppException(ResultEnum.SYS_ERROR,"插入失败");
        }
    }

    @Override
    public Long deleteMarketerAccountById(Long id) {
        log.info("正在删除id为{}的marketerAccount",id);
        if (super.removeById(id)) {
            log.info("删除id为{}的marketerAccount成功",id);
            return id;
        } else {
            log.error("删除id为{}的marketerAccount失败",id);
            throw new AppException(ResultEnum.SYS_ERROR,"删除失败[id=" + id + "]");
        }
    }

    @Override
    public Long updateMarketerAccount(MarketerAccount marketerAccount) {
        log.info("正在更新id为{}的marketerAccount",marketerAccount.getId());
        if (super.updateById(marketerAccount)) {
            log.info("更新d为{}的marketerAccount成功",marketerAccount.getId());
            return marketerAccount.getId();
        } else {
            log.error("更新id为{}的marketerAccount失败",marketerAccount.getId());
            throw new AppException(ResultEnum.SYS_ERROR,"更新失败[id=" + marketerAccount.getId() + "]");
        }
    }

}
