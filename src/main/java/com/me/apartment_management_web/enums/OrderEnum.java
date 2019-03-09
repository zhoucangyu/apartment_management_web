package com.me.apartment_management_web.enums;

public enum OrderEnum {

    ASC(0, "ASC"),
    DESC(1, "DESC");

    public Integer type;

    public String name;

    OrderEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

}
