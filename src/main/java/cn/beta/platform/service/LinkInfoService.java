package cn.beta.platform.service;

import cn.beta.platform.entity.LinkInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* <p>
* 推广链接 服务类
* </p>
*
* @author beta
* @since 2023-10-22
*/
public interface LinkInfoService {

    /**
    * 分页查询LinkInfo
    *
    * @param page     当前页数
    * @param pageSize 页的大小
    * @param factor  搜索关键词
    * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
    * @author beta
    * @since 2023-10-22
    */
    Page<LinkInfo> listLinkInfosByPage(int page, int pageSize, String factor);

    /**
    * 根据id查询LinkInfo
    *
    * @param id 需要查询的LinkInfo的id
    * @return 返回对应id的LinkInfo对象
    * @author beta
    * @since 2023-10-22
    */
    LinkInfo getLinkInfoById(Long id);

    /**
    * 插入LinkInfo
    *
    * @param linkInfo 需要插入的LinkInfo对象
    * @return 返回插入成功之后LinkInfo对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long insertLinkInfo(LinkInfo linkInfo);

    /**
    * 根据id删除LinkInfo
    *
    * @param id 需要删除的LinkInfo对象的id
    * @return 返回被删除的LinkInfo对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long deleteLinkInfoById(Long id);

    /**
    * 根据id更新LinkInfo
    *
    * @param linkInfo 需要更新的LinkInfo对象
    * @return 返回被更新的LinkInfo对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long updateLinkInfo(LinkInfo linkInfo);

}
