package cn.beta.platform.service;

import cn.beta.platform.entity.MarketerInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* <p>
* 营销商信息表 服务类
* </p>
*
* @author beta
* @since 2023-10-22
*/
public interface MarketerInfoService {

    /**
    * 分页查询MarketerInfo
    *
    * @param page     当前页数
    * @param pageSize 页的大小
    * @param factor  搜索关键词
    * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
    * @author beta
    * @since 2023-10-22
    */
    Page<MarketerInfo> listMarketerInfosByPage(int page, int pageSize, String factor);

    /**
    * 根据id查询MarketerInfo
    *
    * @param id 需要查询的MarketerInfo的id
    * @return 返回对应id的MarketerInfo对象
    * @author beta
    * @since 2023-10-22
    */
    MarketerInfo getMarketerInfoById(Long id);

    /**
    * 插入MarketerInfo
    *
    * @param marketerInfo 需要插入的MarketerInfo对象
    * @return 返回插入成功之后MarketerInfo对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long insertMarketerInfo(MarketerInfo marketerInfo);

    /**
    * 根据id删除MarketerInfo
    *
    * @param id 需要删除的MarketerInfo对象的id
    * @return 返回被删除的MarketerInfo对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long deleteMarketerInfoById(Long id);

    /**
    * 根据id更新MarketerInfo
    *
    * @param marketerInfo 需要更新的MarketerInfo对象
    * @return 返回被更新的MarketerInfo对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long updateMarketerInfo(MarketerInfo marketerInfo);

}
