package com.me.apartment_management_web.bean;

import com.me.apartment_management_web.enums.OrderEnum;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageParam {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Map<String, Object> conditionMap = new LinkedHashMap<>();

    private Map<String, OrderEnum> orderMap = new LinkedHashMap<>();

    private Map<String, RangeCondition> rangeMap = new LinkedHashMap<>();

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> getConditionMap() {
        return conditionMap;
    }

    public Map<String, OrderEnum> getOrderMap() {
        return orderMap;
    }

    public Map<String, RangeCondition> getRangeMap() {
        return rangeMap;
    }

}
