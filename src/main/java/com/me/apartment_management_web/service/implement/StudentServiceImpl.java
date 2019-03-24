package com.me.apartment_management_web.service.implement;

import com.me.apartment_management_web.bean.StudentDetail;
import com.me.apartment_management_web.dao.*;
import com.me.apartment_management_web.entity.*;
import com.me.apartment_management_web.enums.*;
import com.me.apartment_management_web.service.IStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
}
