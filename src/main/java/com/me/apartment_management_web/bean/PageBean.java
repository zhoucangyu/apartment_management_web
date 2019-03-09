package com.me.apartment_management_web.bean;

import java.util.List;

public class PageBean<T> {

    private List<T> recordList;

    private Integer pageNum;

    private Integer pageSize;

    private Integer totalCount;

    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
