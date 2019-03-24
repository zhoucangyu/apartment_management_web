package com.me.apartment_management_web.enums;

public enum AccessTypeEnum {

    IN(0, "出"),
    OUT(1, "入");

    private Integer type;

    private String name;

    AccessTypeEnum(Integer type, String name) {
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
        for (AccessTypeEnum accessType : AccessTypeEnum.values()) {
            if (accessType.type.equals(type)) {
                return accessType.name;
            }
        }
        return null;
    }

}
