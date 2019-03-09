package com.me.apartment_management_web.service.implement;

import com.me.apartment_management_web.bean.AccommodationDetail;
import com.me.apartment_management_web.bean.DormitoryDetail;
import com.me.apartment_management_web.bean.SelectOption;
import com.me.apartment_management_web.dao.DormitoryInfoDao;
import com.me.apartment_management_web.entity.DormitoryInfo;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.service.IApartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ApartmentServiceImpl implements IApartmentService {

    @Resource
    private DormitoryInfoDao dormitoryInfoDao;

    @Override
    public List<SelectOption> getAllApartmentName() {
        return null;
    }

    @Override
    public List<DormitoryDetail> getDormitoryDetail(Integer apartmentId, Integer roomName, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<AccommodationDetail> getAccommodationNow(Integer roomId) {
        return null;
    }

    @Override
    public List<AccommodationDetail> getAccommodationHistory(Integer roomId) {
        return null;
    }

    @Override
    public List<DormitoryInfo> getByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap) {
        return dormitoryInfoDao.listByCondition(conditionMap, orderMap);
    }

}
