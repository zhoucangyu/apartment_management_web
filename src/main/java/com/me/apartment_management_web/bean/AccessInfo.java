package com.me.apartment_management_web.bean;

import java.util.Date;

public class AccessInfo {

    private Date time;

    private String accessType;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

}
