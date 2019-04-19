package com.me.apartment_management_web.enums;

public enum IsCheckedEnum {

    IS_CHECKED(1, "已检测"),
    NOT_CHECKED(0, "未检测");

    private Integer type;

    private String name;

    IsCheckedEnum(Integer type, String name) {
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
        for (IsCheckedEnum isChecked : IsCheckedEnum.values()) {
            if (isChecked.type.equals(type)) {
                return isChecked.name;
            }
        }
        return null;
    }

}
