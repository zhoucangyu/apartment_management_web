package com.me.apartment_management_web.service.implement;

import com.me.apartment_management_web.bean.*;
import com.me.apartment_management_web.dao.*;
import com.me.apartment_management_web.entity.*;
import com.me.apartment_management_web.enums.*;
import com.me.apartment_management_web.service.IStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class StudentServiceImpl implements IStudentService {

    // 注入dao层实现
    @Resource
    private StudentInfoDao studentInfoDao;

    @Resource
    private BelongDao belongDao;

    @Resource
    private AccommodationDao accommodationDao;

    @Resource
    private ClassInfoDao classInfoDao;

    @Resource
    private DormitoryInfoDao dormitoryInfoDao;

    @Resource
    private ApartmentInfoDao apartmentInfoDao;

    @Resource
    private AccessDao accessDao;

    @Override
    public StudentDetail getStudentDetail(Integer studentId) {

        // 新建空白StudentDetail对象
        StudentDetail studentDetail = new StudentDetail();

        // 新建学号查询条件
        Map<String, Object> studentConditionMap = new LinkedHashMap<>();
        studentConditionMap.put("student_id", studentId);
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();

        // 查询并插入studentId,studentName,sex,pic
        List<StudentInfo> list1 = studentInfoDao.listByCondition(studentConditionMap, orderMap);
        StudentInfo studentInfo = list1.get(0);
        studentDetail.setStudentId(studentId);
        studentDetail.setStudentName(studentInfo.getName());
        studentDetail.setPic(studentInfo.getPic());
        studentDetail.setSex(SexEnum.getNameByType(studentInfo.getSex()));

        // 查询并插入classId
        List<Belong> list2 = belongDao.listByCondition(studentConditionMap, orderMap);
        Belong belong = list2.get(0);
        Integer classId = belong.getClassId();
        studentDetail.setClassId(classId);

        // 查询roomId,查询并插入moveInTime
        Map<String, Object> accommodationConditionMap = new LinkedHashMap<>();
        accommodationConditionMap.put("student_id", studentId);
        accommodationConditionMap.put("is_in", IsInEnum.IS_IN.getType());
        List<Accommodation> list3 = accommodationDao.listByCondition(accommodationConditionMap, orderMap);
        Accommodation accommodation = list3.get(0);
        Integer roomId = accommodation.getRoomId();
        studentDetail.setMoveInTime(accommodation.getMoveInTime());

        // 查询并插入school,major,teacher,counselor
        Map<String, Object> classConditionMap = new LinkedHashMap<>();
        classConditionMap.put("class_id", classId);
        List<ClassInfo> list4 = classInfoDao.listByCondition(classConditionMap, orderMap);
        ClassInfo classInfo = list4.get(0);
        String teacherName = classInfo.getTeacherName();
        long teacherPhoneNum = classInfo.getTeacherPhoneNum();
        String teacher = teacherName + "  " + teacherPhoneNum;
        String counselorName = classInfo.getCounselorName();
        long counselorPhoneNum = classInfo.getCounselorPhoneNum();
        String counselor = counselorName + "  " + counselorPhoneNum;
        studentDetail.setTeacher(teacher);
        studentDetail.setCounselor(counselor);
        studentDetail.setSchool(SchoolEnum.getNameByType(classInfo.getSchool()));
        studentDetail.setMajor(MajorEnum.getNameByType(classInfo.getMajor()));

        // 查询apartmentId,查询并插入roomName
        Map<String, Object> dormitoryConditionMap = new LinkedHashMap<>();
        dormitoryConditionMap.put("room_id", roomId);
        List<DormitoryInfo> list5 = dormitoryInfoDao.listByCondition(dormitoryConditionMap, orderMap);
        DormitoryInfo dormitoryInfo = list5.get(0);
        Integer apartmentId = dormitoryInfo.getApartmentId();
        studentDetail.setRoomName(dormitoryInfo.getRoomName());

        // 查询并插入apartmentName,principal
        Map<String, Object> apartmentConditionMap = new LinkedHashMap<>();
        apartmentConditionMap.put("apartment_id", apartmentId);
        List<ApartmentInfo> list6 = apartmentInfoDao.listByCondition(apartmentConditionMap, orderMap);
        ApartmentInfo apartmentInfo = list6.get(0);
        String principalName = apartmentInfo.getPrincipalName();
        long principalPhoneNum = apartmentInfo.getPrincipalPhoneNum();
        String principal = principalName + "  " + principalPhoneNum;
        studentDetail.setPrincipal(principal);
        studentDetail.setApartmentName(apartmentInfo.getApartmentName());

        // 返回结果
        return studentDetail;

    }

    @Override
    public PageBean<AccessInfo> getAccessInfo(PageParam pageParam) {

        // 分页查询学生出入信息
        List<Access> list = accessDao.listByPage(pageParam);

        // 查询学生出入信息总数
        int count = accessDao.countByPage(pageParam);

        // 转化查询出的出入信息数据
        PageBean<AccessInfo> pageBean = new PageBean<>();

        List<AccessInfo> newList = new ArrayList<>();

        for (Access item : list) {
            AccessInfo newItem = new AccessInfo();
            newItem.setTime(item.getTime());
            newItem.setAccessType(AccessTypeEnum.getEnumByType(item.getAccessType()).getName());
            newList.add(newItem);
        }

        // 整合成PageBean
        pageBean.setPageNum(pageParam.getPageNum());
        pageBean.setPageSize(pageParam.getPageSize());
        pageBean.setTotalCount(count);
        pageBean.setRecordList(newList);

        // 返回结果
        return pageBean;

    }

    @Override
    public List<SelectOption> getAllSchool() {

        // 新建返回结果
        List<SelectOption> list = new ArrayList<>();

        // 循环插入数据
        for (SchoolEnum school : SchoolEnum.values()) {
            SelectOption selectOption = new SelectOption();
            selectOption.setKey(school.getType());
            selectOption.setValue(school.getName());
            list.add(selectOption);
        }

        // 返回结果
        return list;

    }

    @Override
    public List<SelectOption> getAllMajor() {

        // 新建返回结果
        List<SelectOption> list = new ArrayList<>();

        // 循环插入数据
        for (MajorEnum major : MajorEnum.values()) {
            SelectOption selectOption = new SelectOption();
            selectOption.setKey(major.getType());
            selectOption.setValue(major.getName());
            list.add(selectOption);
        }

        // 返回结果
        return list;

    }

    @Override
    public Integer isInApartment(Integer studentId) {

        // 插入查询条件studentId
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        conditionMap.put("student_id", studentId);

        // 插入排序条件按时间倒序
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        orderMap.put("time", OrderEnum.DESC);

        // 查询结果
        List<Access> list = accessDao.listByCondition(conditionMap, orderMap);

        // 若查询不到该学生出入记录，返回空值
        if (list == null || list.isEmpty()) {
            return null;
        }

        // 若查询到了，将最近的学生出入记录作为结果返回
        return list.get(0).getAccessType();

    }

    @Override
    public List<Integer> listStudentId(Integer school, Integer major, Integer classId, Integer apartmentId, Integer roomName, Integer state, Integer studentId, String name, Integer sex) {

        // 查询t_class_info表中符合条件的classId
        Map<String, OrderEnum> orderMap1 = new LinkedHashMap<>();
        orderMap1.put("class_id", OrderEnum.ASC);

        Map<String, Object> conditionMap1 = new LinkedHashMap<>();

        if (school != null ) {
            conditionMap1.put("school", school);
        }
        if (major != null) {
            conditionMap1.put("major", major);
        }
        if (classId != null) {
            conditionMap1.put("class_id", classId);
        }

        List<ClassInfo> list1 = classInfoDao.listByCondition(conditionMap1, orderMap1);

        Map<String, OrderEnum> orderMap2 = new LinkedHashMap<>();
        orderMap2.put("student_id", OrderEnum.ASC);

        // 根据查出的classId查询包括的studentId
        List<Belong> list2 = new ArrayList<>();
        if (list1 != null && !list1.isEmpty()) {
            for (ClassInfo item : list1) {
                Map<String, Object> conditionMap2 = new LinkedHashMap<>();
                conditionMap2.put("class_id", item.getClassId());
                List<Belong> list3 = belongDao.listByCondition(conditionMap2, orderMap2);
                list2.addAll(list3);
            }
        }

        // 查询t_dormitory表中符合条件的roomId
        Map<String, OrderEnum> orderMap3 = new LinkedHashMap<>();
        orderMap3.put("room_id", OrderEnum.ASC);

        Map<String, Object> conditionMap3 = new LinkedHashMap<>();
        if (apartmentId != null) {
            conditionMap3.put("apartment_id", apartmentId);
        }
        if (roomName != null) {
            conditionMap3.put("room_name", roomName);
        }

        List<DormitoryInfo> list4 = dormitoryInfoDao.listByCondition(conditionMap3, orderMap3);

        // 根据查出的roomId查询包括的studentId
        List<Accommodation> list5 = new ArrayList<>();
        if (list4 != null && !list4.isEmpty()) {
            for (DormitoryInfo item : list4) {
                Map<String, Object> conditionMap4 = new LinkedHashMap<>();
                conditionMap4.put("room_id", item.getRoomId());
                conditionMap4.put("is_in", IsInEnum.IS_IN.getType());
                List<Accommodation> list6 = accommodationDao.listByCondition(conditionMap4, orderMap3);
                list5.addAll(list6);
            }
        }

        // 查询t_access表中符合条件的student_id
        List<Access> list7 = accessDao.getAllStudentId();

        List<Access> list8 = list7;

        if (state != null) {
            list8 = new ArrayList<>();
            for (Access access : list7) {
                if (state.equals(isInApartment(access.getStudentId()))) {
                    list8.add(access);
                }
            }
        }

        // 查询t_student_info表中符合条件的studentId
        Map<String, Object> conditionMap4 = new LinkedHashMap<>();
        if (studentId != null) {
            conditionMap4.put("student_id", studentId);
        }
        if (name != null) {
            conditionMap4.put("name", name);
        }
        if (sex != null) {
            conditionMap4.put("sex", sex);
        }

        List<StudentInfo> list9 = studentInfoDao.listByCondition(conditionMap4, orderMap2);

        // 将list2, list5, list8, list9集合成一个studentId List
        List<Integer> studentList = new ArrayList<>();
        if (list2.isEmpty() || list5.isEmpty() || list8.isEmpty() || list9.isEmpty()) {
            return studentList;
        }
        List<Integer> intList1 = new ArrayList<>();
        List<Integer> intList2 = new ArrayList<>();
        List<Integer> intList3 = new ArrayList<>();
        List<Integer> intList4 = new ArrayList<>();
        for (Belong item : list2) {
            intList1.add(item.getStudentId());
        }
        for (Accommodation item : list5) {
            intList2.add(item.getStudentId());
        }
        for (Access item : list8) {
            intList3.add(item.getStudentId());
        }
        for (StudentInfo item : list9) {
            intList4.add(item.getStudentId());
        }
        for (Integer i : intList1) {
            if (!intList2.contains(i) || !intList3.contains(i) || !intList4.contains(i)) {
                continue;
            }
            studentList.add(i);
        }

        // 返回结果
        return studentList;

    }

    @Override
    public PageBean<StudentSummary> getStudentSummary(List<Integer> studentList, Integer pageNum, Integer pageSize) {

        // 若studentList为空，返回空值
        if (studentList == null) {
            return null;
        }

        // 新建查询出的结果List
        List<StudentSummary> allList = new ArrayList<>();

        // 循环查询并插入值
        for (Integer studentId : studentList) {

            StudentSummary studentSummary = new StudentSummary();

            studentSummary.setStudentId(studentId);

            Map<String, Object> conditionMap1 = new LinkedHashMap<>();
            conditionMap1.put("student_id", studentId);

            List<StudentInfo> list1 = studentInfoDao.listByCondition(conditionMap1, new LinkedHashMap<>());
            if (!list1.isEmpty()) {
                studentSummary.setName(list1.get(0).getName());
                studentSummary.setSex(SexEnum.getNameByType(list1.get(0).getSex()));
            }

            List<Belong> list2 = belongDao.listByCondition(conditionMap1, new LinkedHashMap<>());
            if (!list2.isEmpty()) {

                studentSummary.setClassId(list2.get(0).getClassId());

                Map<String, Object> conditionMap2 = new LinkedHashMap<>();
                conditionMap2.put("class_id", list2.get(0).getClassId());
                List<ClassInfo> list3 = classInfoDao.listByCondition(conditionMap2, new LinkedHashMap<>());

                studentSummary.setSchool(SchoolEnum.getNameByType(list3.get(0).getSchool()));
                studentSummary.setMajor(MajorEnum.getNameByType(list3.get(0).getMajor()));
            }

            Map<String, Object> conditionMap3 = new LinkedHashMap<>();
            conditionMap3.put("student_id", studentId);
            conditionMap3.put("is_in", IsInEnum.IS_IN.getType());
            List<Accommodation> list4 = accommodationDao.listByCondition(conditionMap3, new LinkedHashMap<>());
            if (!list4.isEmpty()) {

                Map<String, Object> conditionMap4 = new LinkedHashMap<>();
                conditionMap4.put("room_id", list4.get(0).getRoomId());
                List<DormitoryInfo> list5 = dormitoryInfoDao.listByCondition(conditionMap4, new LinkedHashMap<>());
                studentSummary.setRoomName(list5.get(0).getRoomName());

                Map<String, Object> conditionMap5 = new LinkedHashMap<>();
                conditionMap5.put("apartment_id", list5.get(0).getApartmentId());
                List<ApartmentInfo> list6 = apartmentInfoDao.listByCondition(conditionMap5, new LinkedHashMap<>());
                studentSummary.setApartmentName(list6.get(0).getApartmentName());
            }

            AccessTypeEnum accessTypeEnum = AccessTypeEnum.getEnumByType(isInApartment(studentId));
            if (accessTypeEnum != null) {
                studentSummary.setState(accessTypeEnum.getState());
            }

            allList.add(studentSummary);

        }

        // 根据pageNum, pageSize从allList中取出对应部分
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }

        // 若查询位置的索引超过最大索引，对其进行调整
        List<StudentSummary> recordList = allList.subList((pageNum-1)*pageSize, Math.min(pageNum*pageSize, allList.size()));

        // 新建返回结果并插入数据
        PageBean<StudentSummary> pageBean = new PageBean<>();
        pageBean.setRecordList(recordList);
        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalCount(allList.size());

        // 返回结果
        return pageBean;

    }

}
