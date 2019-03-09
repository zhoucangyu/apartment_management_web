package com.me.apartment_management_web.bean;

import java.util.Date;

public class AccommodationDetail {

    private Integer studentId;

    private String studentName;

    private Date moveInTime;

    private Date moveOutTime;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getMoveInTime() {
        return moveInTime;
    }

    public void setMoveInTime(Date moveInTime) {
        this.moveInTime = moveInTime;
    }

    public Date getMoveOutTime() {
        return moveOutTime;
    }

    public void setMoveOutTime(Date moveOutTime) {
        this.moveOutTime = moveOutTime;
    }
}
