package com.me.apartment_management_web.enums;

public enum IsHandledEnum {

    IS_HANDLED(1, "已处理"),
    NOT_HANDLED(0, "未处理");

    private Integer type;

    private String name;

    IsHandledEnum(Integer type, String name) {
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
        for (IsHandledEnum isHandled : IsHandledEnum.values()) {
            if (isHandled.type.equals(type)) {
                return isHandled.name;
            }
        }
        return null;
    }

}
