package com.me.apartment_management_web.service.implement;

import com.me.apartment_management_web.bean.*;
import com.me.apartment_management_web.dao.AccommodationDao;
import com.me.apartment_management_web.dao.ApartmentInfoDao;
import com.me.apartment_management_web.dao.DormitoryInfoDao;
import com.me.apartment_management_web.dao.StudentInfoDao;
import com.me.apartment_management_web.entity.Accommodation;
import com.me.apartment_management_web.entity.ApartmentInfo;
import com.me.apartment_management_web.entity.DormitoryInfo;
import com.me.apartment_management_web.entity.StudentInfo;
import com.me.apartment_management_web.enums.IsInEnum;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.service.IApartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApartmentServiceImpl implements IApartmentService {

    // 注入dao层实现
    @Resource
    private DormitoryInfoDao dormitoryInfoDao;

    @Resource
    private ApartmentInfoDao apartmentInfoDao;

    @Resource
    private AccommodationDao accommodationDao;

    @Resource
    private StudentInfoDao studentInfoDao;

    @Override
    public List<SelectOption> getAllApartmentName() {

        // 查询条件：不限，即查询所有项目
        Map<String, Object> conditionMap = new LinkedHashMap<>();

        // 排序条件：apartment_id升序
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        orderMap.put("apartment_id", OrderEnum.ASC);

        // 查询项目
        List<ApartmentInfo> list = apartmentInfoDao.listByCondition(conditionMap, orderMap);

        // 对查询结果进行转化
        List<SelectOption> newList = new ArrayList<>();
        for (ApartmentInfo item : list) {
            SelectOption newItem = new SelectOption();
            newItem.setKey(item.getApartmentId());
            newItem.setValue(item.getApartmentName());
            newList.add(newItem);
        }

        // 返回结果
        return newList;
    }

    @Override
    public PageBean<DormitoryDetail> getDormitoryDetail(PageParam pageParam) {

        // 分页查询宿舍信息
        List<DormitoryInfo> list = dormitoryInfoDao.listByPage(pageParam);

        // 查询宿舍信息总数
        int count = dormitoryInfoDao.countByPage(pageParam);

        // 根据apartmentId查询apartmentName并集合数据
        PageBean<DormitoryDetail> pageBean = new PageBean<>();
        List<DormitoryDetail> newList = new ArrayList<>();

        // 循环向DormitoryDetail里插入数据
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        for (DormitoryInfo item : list) {
            DormitoryDetail newItem = new DormitoryDetail();
            newItem.setRoomId(item.getRoomId());
            newItem.setRoomName(item.getRoomName());
            int apartmentId = item.getApartmentId();
            conditionMap.clear();
            conditionMap.put("apartment_id", apartmentId);
            List<ApartmentInfo> l = apartmentInfoDao.listByCondition(conditionMap, orderMap);
            newItem.setApartmentName(l.get(0).getApartmentName());
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
    public List<AccommodationDetail> getAccommodationNow(Integer roomId) {

        // 通过roomId查询宿舍当前居住情况
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        // 查询条件：room_id，正在居住
        conditionMap.put("room_id", roomId);
        conditionMap.put("is_in", IsInEnum.IS_IN.getType());
        // 排序条件：学号升序
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        orderMap.put("student_id", OrderEnum.ASC);

        // 查询结果
        List<Accommodation> list = accommodationDao.listByCondition(conditionMap, orderMap);

        // 根据studentId查询studentName并整合数据
        List<AccommodationDetail> newList = new ArrayList<>();

        // 循环向AccommodationDetail里插入数据
        for (Accommodation item: list) {
            AccommodationDetail newItem = new AccommodationDetail();
            int studentId = item.getStudentId();
            newItem.setStudentId(studentId);
            newItem.setMoveInTime(item.getMoveInTime());
            Map<String, Object> cMap = new LinkedHashMap<>();
            cMap.put("student_id", studentId);
            Map<String, OrderEnum> oMap = new LinkedHashMap<>();
            List<StudentInfo> l = studentInfoDao.listByCondition(cMap, oMap);
            newItem.setStudentName(l.get(0).getName());
            newList.add(newItem);
        }

        // 返回结果
        return newList;

    }

    @Override
    public List<AccommodationDetail> getAccommodationHistory(Integer roomId) {

        // 通过roomId查询宿舍历史居住情况
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        // 查询条件：room_id，正在居住
        conditionMap.put("room_id", roomId);
        conditionMap.put("is_in", IsInEnum.NOT_IN.getType());
        // 排序条件：学号升序
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        orderMap.put("student_id", OrderEnum.ASC);

        // 查询结果
        List<Accommodation> list = accommodationDao.listByCondition(conditionMap, orderMap);

        // 根据studentId查询studentName并整合数据
        List<AccommodationDetail> newList = new ArrayList<>();

        // 循环向AccommodationDetail里插入数据
        for (Accommodation item: list) {
            AccommodationDetail newItem = new AccommodationDetail();
            int studentId = item.getStudentId();
            newItem.setStudentId(studentId);
            newItem.setMoveInTime(item.getMoveInTime());
            newItem.setMoveOutTime(item.getMoveOutTime());
            Map<String, Object> cMap = new LinkedHashMap<>();
            cMap.put("student_id", studentId);
            Map<String, OrderEnum> oMap = new LinkedHashMap<>();
            List<StudentInfo> l = studentInfoDao.listByCondition(cMap, oMap);
            newItem.setStudentName(l.get(0).getName());
            newList.add(newItem);
        }

        // 返回结果
        return newList;

    }
}
