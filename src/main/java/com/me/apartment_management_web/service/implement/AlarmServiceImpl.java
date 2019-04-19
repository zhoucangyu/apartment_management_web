package com.me.apartment_management_web.service.implement;

import com.me.apartment_management_web.bean.AlarmInfo;
import com.me.apartment_management_web.bean.PageBean;
import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.bean.SelectOption;
import com.me.apartment_management_web.dao.*;
import com.me.apartment_management_web.entity.*;
import com.me.apartment_management_web.enums.AlarmTypeEnum;
import com.me.apartment_management_web.enums.IsHandledEnum;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.service.IAlarmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlarmServiceImpl implements IAlarmService {

    // 注入dao层实现
    @Resource
    private AlarmDao alarmDao;

    @Resource
    private ApartmentInfoDao apartmentInfoDao;

    @Resource
    private StudentInfoDao studentInfoDao;

    @Resource
    private AccommodationDao accommodationDao;

    @Resource
    private DormitoryInfoDao dormitoryInfoDao;

    @Override
    public List<SelectOption> getAlarmType() {

        // 新建返回结果
        List<SelectOption> list = new ArrayList<>();

        // 循环插入数据
        for (AlarmTypeEnum alarmType : AlarmTypeEnum.values()) {
            SelectOption selectOption = new SelectOption();
            selectOption.setKey(alarmType.getType());
            selectOption.setValue(alarmType.getName());
            list.add(selectOption);
        }

        // 返回结果
        return list;

    }

    @Override
    public List<SelectOption> getIsHandled() {

        // 新建返回结果
        List<SelectOption> list = new ArrayList<>();

        // 循环插入数据
        for (IsHandledEnum isHandled : IsHandledEnum.values()) {
            SelectOption selectOption = new SelectOption();
            selectOption.setKey(isHandled.getType());
            selectOption.setValue(isHandled.getName());
            list.add(selectOption);
        }

        // 返回结果
        return list;

    }

    @Override
    public PageBean<AlarmInfo> getAlarmInfo(PageParam pageParam) {

        // 分页查询报警信息
        List<Alarm> list = alarmDao.listByPage(pageParam);

        // 查询报警信息总数
        int count = alarmDao.countByPage(pageParam);

        // 根据studentId查询name,apartmentName并集合数据
        PageBean<AlarmInfo> pageBean = new PageBean<>();
        List<AlarmInfo> newList = new ArrayList<>();

        // 循环向AlarmInfo里插入数据
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        for (Alarm item : list) {
            AlarmInfo newItem = new AlarmInfo();
            newItem.setId(item.getId());
            newItem.setAlarmType(AlarmTypeEnum.getNameByType(item.getAlarmType()));
            newItem.setIsHandled(IsHandledEnum.getNameByType(item.getIsHandled()));
            newItem.setStartTime(item.getStartTime());
            newItem.setEndTime(item.getEndTime());
            int studentId = item.getStudentId();
            newItem.setStudentId(studentId);
            conditionMap.clear();
            conditionMap.put("student_id", studentId);
            List<StudentInfo> studentInfoList = studentInfoDao.listByCondition(conditionMap, orderMap);
            String name = studentInfoList.get(0).getName();
            newItem.setName(name);
            List<Accommodation> accommodationList = accommodationDao.listByCondition(conditionMap, orderMap);
            int roomId = accommodationList.get(0).getRoomId();
            conditionMap.clear();
            conditionMap.put("room_id", roomId);
            List<DormitoryInfo> dormitoryInfoList = dormitoryInfoDao.listByCondition(conditionMap, orderMap);
            int apartmentId = dormitoryInfoList.get(0).getApartmentId();
            conditionMap.clear();
            conditionMap.put("apartment_id", apartmentId);
            List<ApartmentInfo> apartmentInfoList = apartmentInfoDao.listByCondition(conditionMap, orderMap);
            String apartmentName = apartmentInfoList.get(0).getApartmentName();
            newItem.setApartmentName(apartmentName);
            newList.add(newItem);
        }

        // 整合成PageBean
        pageBean.setRecordList(newList);
        pageBean.setTotalCount(count);
        pageBean.setPageNum(pageParam.getPageNum());
        pageBean.setPageSize(pageParam.getPageSize());

        // 返回结果
        return pageBean;

    }

    @Override
    public boolean handleAlarmById(Integer id) {

        // 获取该id对应的Alarm实体
        Alarm alarm = alarmDao.getById(id);

        // 若获取为空，或Alarm实体不是未被处理的，则返回false
        if (alarm == null || !IsHandledEnum.NOT_HANDLED.getType().equals(alarm.getIsHandled())) {
            return false;
        }

        // 若Alarm实体未被处理，则设置为已处理并保存
        alarm.setIsHandled(IsHandledEnum.IS_HANDLED.getType());
        alarmDao.update(alarm);

        // 返回true
        return true;

    }

}
