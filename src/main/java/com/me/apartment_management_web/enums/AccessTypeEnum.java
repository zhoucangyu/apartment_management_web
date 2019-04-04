package com.me.apartment_management_web.enums;

public enum AccessTypeEnum {

    OUT(0, "出", "公寓楼外"),
    IN(1, "入", "公寓楼内");

    private Integer type;

    private String name;

    private String state;

    AccessTypeEnum(Integer type, String name, String state) {
        this.type = type;
        this.name = name;
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public static AccessTypeEnum getEnumByType(Integer type) {
        for (AccessTypeEnum accessType : AccessTypeEnum.values()) {
            if (accessType.type.equals(type)) {
                return accessType;
            }
        }
        return null;
    }

}
