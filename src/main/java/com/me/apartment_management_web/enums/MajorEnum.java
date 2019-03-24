package com.me.apartment_management_web.enums;

public enum MajorEnum {

    COMPUTER_SCIENCE(1, "计算机科学"),
    SOFTWARE_ENGINEERING(2, "软件工程"),
    COMMUNICATION_ENGINEERING(3, "通信工程");

    private Integer type;

    private String name;

    MajorEnum(Integer type, String name) {
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
        for (MajorEnum major : MajorEnum.values()) {
            if (major.type.equals(type)) {
                return major.name;
            }
        }
        return null;
    }
}
