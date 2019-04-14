package com.me.apartment_management_web.enums;

public enum VisitorTypeEnum {

    TEACHER(0, "教师"),
    COUNSELOR(1, "辅导员"),
    WORKER(2, "教职工"),
    STUDENT(3, "学生"),
    OTHER(4, "其他");

    private Integer type;

    private String name;

    VisitorTypeEnum(Integer type, String name) {
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
        for (VisitorTypeEnum visitorTypeEnum : VisitorTypeEnum.values()) {
            if (visitorTypeEnum.type.equals(type)) {
                return visitorTypeEnum.name;
            }
        }
        return null;
    }

}
