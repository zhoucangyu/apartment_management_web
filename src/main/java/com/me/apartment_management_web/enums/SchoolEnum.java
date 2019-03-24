package com.me.apartment_management_web.enums;

public enum SchoolEnum {

    SCHOOL_OF_COMPUTER_SCIENCE(1, "计算机学院"),
    SCHOOL_OF_INFORMATION_AND_COMMUNICATION_ENGINEERING(2, "信息与通信工程学院");

    private Integer type;

    private String name;

    SchoolEnum(Integer type, String name) {
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
        for (SchoolEnum school : SchoolEnum.values()) {
            if (school.type.equals(type)) {
                return school.name;
            }
        }
        return null;
    }

}
