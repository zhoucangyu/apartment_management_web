package com.me.apartment_management_web.enums;

public enum SexEnum {

    MALE(0, "男"),
    FEMALE(1, "女");

    private Integer type;

    private String name;

    SexEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getNameByType(Integer type) {
        for (SexEnum sex : SexEnum.values()) {
            if (sex.type.equals(type)) {
                return sex.name;
            }
        }
        return null;
    }

}
