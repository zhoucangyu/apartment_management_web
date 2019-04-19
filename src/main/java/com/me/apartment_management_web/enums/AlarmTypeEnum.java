package com.me.apartment_management_web.enums;

public enum AlarmTypeEnum {

    STAY_OUT(0, "晚出不归"),
    STAY_IN(1, "连续一天未出公寓");

    private Integer type;

    private String name;

    AlarmTypeEnum(Integer type, String name) {
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
        for (AlarmTypeEnum alarmType : AlarmTypeEnum.values()) {
            if (alarmType.type.equals(type)) {
                return alarmType.name;
            }
        }
        return null;
    }

}
