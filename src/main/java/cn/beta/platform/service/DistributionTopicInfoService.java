package cn.beta.platform.service;

import cn.beta.platform.entity.DistributionTopicInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* <p>
* 分销基础专题信息 服务类
* </p>
*
* @author beta
* @since 2023-10-22
*/
public interface DistributionTopicInfoService {

    /**
    * 分页查询DistributionTopicInfo
    *
    * @param page     当前页数
    * @param pageSize 页的大小
    * @param factor  搜索关键词
    * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
    * @author beta
    * @since 2023-10-22
    */
    Page<DistributionTopicInfo> listDistributionTopicInfosByPage(int page, int pageSize, String factor);

    /**
    * 根据id查询DistributionTopicInfo
    *
    * @param id 需要查询的DistributionTopicInfo的id
    * @return 返回对应id的DistributionTopicInfo对象
    * @author beta
    * @since 2023-10-22
    */
    DistributionTopicInfo getDistributionTopicInfoById(Long id);

    /**
    * 插入DistributionTopicInfo
    *
    * @param distributionTopicInfo 需要插入的DistributionTopicInfo对象
    * @return 返回插入成功之后DistributionTopicInfo对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long insertDistributionTopicInfo(DistributionTopicInfo distributionTopicInfo);

    /**
    * 根据id删除DistributionTopicInfo
    *
    * @param id 需要删除的DistributionTopicInfo对象的id
    * @return 返回被删除的DistributionTopicInfo对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long deleteDistributionTopicInfoById(Long id);

    /**
    * 根据id更新DistributionTopicInfo
    *
    * @param distributionTopicInfo 需要更新的DistributionTopicInfo对象
    * @return 返回被更新的DistributionTopicInfo对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long updateDistributionTopicInfo(DistributionTopicInfo distributionTopicInfo);

}
