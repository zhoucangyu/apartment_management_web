package com.me.apartment_management_web.service;

import com.me.apartment_management_web.bean.AlarmInfo;
import com.me.apartment_management_web.bean.PageBean;
import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.bean.SelectOption;

import java.util.List;

public interface IAlarmService {

    /**
     * 获取所有报警类型
     * @return
     */
    List<SelectOption> getAlarmType();

    /**
     * 获取是否已被处理过
     * @return
     */
    List<SelectOption> getIsHandled();

    /**
     * 分页获取报警信息
     * @param pageParam
     * @return
     */
    PageBean<AlarmInfo> getAlarmInfo(PageParam pageParam);

    /**
     * 处理该id对应的报警信息
     * @param id
     * @return
     */
    boolean handleAlarmById(Integer id);

}
