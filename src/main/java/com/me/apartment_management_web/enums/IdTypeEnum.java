package com.me.apartment_management_web.enums;

public enum IdTypeEnum {

    STAFF(0, "教工号"),
    STUDENT(1, "学号"),
    ID(2, "身份证号");

    private Integer type;

    private String name;

    IdTypeEnum(Integer type, String name) {
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
        for (IdTypeEnum idTypeEnum : IdTypeEnum.values()) {
            if (idTypeEnum.type.equals(type)) {
                return idTypeEnum.name;
            }
        }
        return null;
    }
}
