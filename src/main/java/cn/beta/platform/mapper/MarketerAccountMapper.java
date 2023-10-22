package cn.beta.platform.mapper;

import cn.beta.platform.entity.MarketerAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
* <p>
* 营销商账户表 Mapper 接口
* </p>
*
* @author beta
* @since 2023-10-22
*/
@Mapper
@Repository
public interface MarketerAccountMapper extends BaseMapper<MarketerAccount> {

}
