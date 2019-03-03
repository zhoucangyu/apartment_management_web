package com.me.apartment_management_web.enums;

public enum IsInEnum {

    IS_IN(1, "正在住宿"),
    NOT_IN(0, "已搬离");

    private Integer type;

    private String name;

    IsInEnum(Integer type, String name) {
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
        for (IsInEnum isIn : IsInEnum.values()) {
            if (isIn.type.equals(type)) {
                return isIn.name;
            }
        }
        return null;
    }

}
