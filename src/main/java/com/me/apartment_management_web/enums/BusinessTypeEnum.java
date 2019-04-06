package com.me.apartment_management_web.enums;

public enum BusinessTypeEnum {

    SICK_LEAVE(0, "病假"),
    PERSONAL_LEAVE(1, "事假"),
    OTHER_LEAVE(2, "其他");

    private Integer type;

    private String name;

    BusinessTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String getNameByType(Integer type) {
        for (BusinessTypeEnum businessType : BusinessTypeEnum.values()) {
            if (businessType.type.equals(type)) {
                return businessType.name;
            }
        }
        return null;
    }

}
