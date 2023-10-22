package cn.beta.platform.mapper;

import cn.beta.platform.entity.MarketerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
* <p>
* 营销商信息表 Mapper 接口
* </p>
*
* @author beta
* @since 2023-10-22
*/
@Mapper
@Repository
public interface MarketerInfoMapper extends BaseMapper<MarketerInfo> {

}
