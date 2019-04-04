package com.me.apartment_management_web.service;

import com.me.apartment_management_web.bean.*;

import java.util.List;

public interface IStudentService {

    /**
     * 获取学生详细信息
     * @param studentId
     * @return
     */
    StudentDetail getStudentDetail(Integer studentId);

    /**
     * 分页获取学生出入信息
     * @param pageParam
     * @return
     */
    PageBean<AccessInfo> getAccessInfo(PageParam pageParam);

    /**
     * 获取所有学院名称
     * @return
     */
    List<SelectOption> getAllSchool();

    /**
     * 获取所有专业名称
     * @return
     */
    List<SelectOption> getAllMajor();

    /**
     * 判断某学生是否在公寓内
     * @param studentId
     * @return
     */
    Integer isInApartment(Integer studentId);

    /**
     * 查询符合条件的studentId
     * @param school
     * @param major
     * @param classId
     * @param apartmentId
     * @param roomName
     * @param state
     * @param studentId
     * @param name
     * @param sex
     * @return
     */
    List<Integer> listStudentId(Integer school, Integer major, Integer classId, Integer apartmentId, Integer roomName, Integer state, Integer studentId, String name, Integer sex);

    /**
     * 通过studentId分页查询学生信息
     * @param studentList
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageBean<StudentSummary> getStudentSummary(List<Integer> studentList, Integer pageNum, Integer pageSize);

}
