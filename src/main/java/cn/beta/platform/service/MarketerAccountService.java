package cn.beta.platform.service;

import cn.beta.platform.entity.MarketerAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
* <p>
* 营销商账户表 服务类
* </p>
*
* @author beta
* @since 2023-10-22
*/
public interface MarketerAccountService {

    /**
    * 分页查询MarketerAccount
    *
    * @param page     当前页数
    * @param pageSize 页的大小
    * @param factor  搜索关键词
    * @return 返回mybatis-plus的Page对象,其中records字段为符合条件的查询结果
    * @author beta
    * @since 2023-10-22
    */
    Page<MarketerAccount> listMarketerAccountsByPage(int page, int pageSize, String factor);

    /**
    * 根据id查询MarketerAccount
    *
    * @param id 需要查询的MarketerAccount的id
    * @return 返回对应id的MarketerAccount对象
    * @author beta
    * @since 2023-10-22
    */
    MarketerAccount getMarketerAccountById(Long id);

    /**
    * 插入MarketerAccount
    *
    * @param marketerAccount 需要插入的MarketerAccount对象
    * @return 返回插入成功之后MarketerAccount对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long insertMarketerAccount(MarketerAccount marketerAccount);

    /**
    * 根据id删除MarketerAccount
    *
    * @param id 需要删除的MarketerAccount对象的id
    * @return 返回被删除的MarketerAccount对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long deleteMarketerAccountById(Long id);

    /**
    * 根据id更新MarketerAccount
    *
    * @param marketerAccount 需要更新的MarketerAccount对象
    * @return 返回被更新的MarketerAccount对象的id
    * @author beta
    * @since 2023-10-22
    */
    Long updateMarketerAccount(MarketerAccount marketerAccount);

}
