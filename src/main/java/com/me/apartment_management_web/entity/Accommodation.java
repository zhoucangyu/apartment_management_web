package com.me.apartment_management_web.entity;

import java.util.Date;

public class Accommodation {

    private Integer id;

    private Integer studentId;

    private Integer roomId;

    private Date moveInTime;

    private Date moveOutTime;

    private Integer isIn;

    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
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

    public Integer getIsIn() {
        return isIn;
    }

    public void setIsIn(Integer isIn) {
        this.isIn = isIn;
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
