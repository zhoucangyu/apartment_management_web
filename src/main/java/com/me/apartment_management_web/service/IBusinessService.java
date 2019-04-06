package com.me.apartment_management_web.service;

import com.me.apartment_management_web.bean.BusinessInfo;
import com.me.apartment_management_web.bean.PageBean;
import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.bean.SelectOption;
import com.me.apartment_management_web.entity.Business;

import java.util.List;

public interface IBusinessService {

    /**
     * 获取所有事务类型
     * @return
     */
    List<SelectOption> getBusinessType();

    /**
     * 新增一个事务
     * @param business
     */
    void addBusiness(Business business);

    /**
     * 修改一个事务
     * @param business
     */
    void modifyBusiness(Business business);

    /**
     * 通过ID获取一个Business实体
     * @param id
     * @return
     */
    Business getById(Integer id);

    /**
     * 分页获取事务信息
     * @param pageParam
     * @return
     */
    PageBean<BusinessInfo> getBusinessInfo(PageParam pageParam);

}
