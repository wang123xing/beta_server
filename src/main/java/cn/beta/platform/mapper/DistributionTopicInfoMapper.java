package cn.beta.platform.mapper;

import cn.beta.platform.entity.DistributionTopicInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
* <p>
* 分销基础专题信息 Mapper 接口
* </p>
*
* @author beta
* @since 2023-10-22
*/
@Mapper
@Repository
public interface DistributionTopicInfoMapper extends BaseMapper<DistributionTopicInfo> {

}
