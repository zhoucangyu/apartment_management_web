package com.me.apartment_management_web.service;

import com.me.apartment_management_web.bean.*;

import java.util.List;

public interface IApartmentService {

    /**
     * 获取所有公寓名称
     * @return
     */
    List<SelectOption> getAllApartmentName();

    /**
     * 查询公寓和宿舍详细信息
     * @param pageParam
     * @return
     */
    PageBean<DormitoryDetail> getDormitoryDetail(PageParam pageParam);

    /**
     * 查询宿舍当前住宿情况
     * @param roomId
     * @return
     */
    List<AccommodationDetail> getAccommodationNow(Integer roomId);

    /**
     * 查询宿舍历史住宿情况
     * @param roomId
     * @return
     */
    List<AccommodationDetail> getAccommodationHistory(Integer roomId);

}
