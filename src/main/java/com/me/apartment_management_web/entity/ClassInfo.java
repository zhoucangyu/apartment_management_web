package com.me.apartment_management_web.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ClassInfo {

    private Integer id;

    private Integer classId;

    private Integer major;

    private Integer school;

    private String teacherName;

    private Long teacherPhoneNum;

    private String counselorName;

    private Long counselorPhoneNum;

    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }

    public Integer getSchool() {
        return school;
    }

    public void setSchool(Integer school) {
        this.school = school;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getTeacherPhoneNum() {
        return teacherPhoneNum;
    }

    public void setTeacherPhoneNum(Long teacherPhoneNum) {
        this.teacherPhoneNum = teacherPhoneNum;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public Long getCounselorPhoneNum() {
        return counselorPhoneNum;
    }

    public void setCounselorPhoneNum(Long counselorPhoneNum) {
        this.counselorPhoneNum = counselorPhoneNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

}
