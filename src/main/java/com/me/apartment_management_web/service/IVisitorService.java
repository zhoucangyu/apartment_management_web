package com.me.apartment_management_web.service;

import com.me.apartment_management_web.bean.PageBean;
import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.bean.SelectOption;
import com.me.apartment_management_web.bean.VisitorInfo;
import com.me.apartment_management_web.entity.Visitor;

import java.util.List;

public interface IVisitorService {

    /**
     * 获取所有访客类型
     * @return
     */
    List<SelectOption> getVisitorType();

    /**
     * 获取所有证件类型
     * @return
     */
    List<SelectOption> getIdType();

    /**
     * 新增一个访客记录
     * @param visitor
     */
    void addVisitor(Visitor visitor);

    /**
     * 修改一个访客记录
     * @param visitor
     */
    void modifyVisitor(Visitor visitor);

    /**
     * 通过ID获取一个Visitor实体
     * @param id
     * @return
     */
    Visitor getById(Integer id);

    /**
     * 分页获取访客记录
     * @param pageParam
     * @return
     */
    PageBean<VisitorInfo> getVisitorInfo(PageParam pageParam);

}
