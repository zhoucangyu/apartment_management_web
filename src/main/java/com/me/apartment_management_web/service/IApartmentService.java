package com.me.apartment_management_web.service;

import com.me.apartment_management_web.bean.AccommodationDetail;
import com.me.apartment_management_web.bean.DormitoryDetail;
import com.me.apartment_management_web.bean.SelectOption;
import com.me.apartment_management_web.entity.DormitoryInfo;
import com.me.apartment_management_web.enums.OrderEnum;

import java.util.List;
import java.util.Map;

public interface IApartmentService {

    /**
     * 获取所有公寓名称
     * @return
     */
    List<SelectOption> getAllApartmentName();

    /**
     * 查询公寓和宿舍详细信息
     * @param apartmentId
     * @param roomName
     * @return
     */
    List<DormitoryDetail> getDormitoryDetail(Integer apartmentId, Integer roomName, Integer pageNum, Integer pageSize);

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

    List<DormitoryInfo> getByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap);

}
