package com.me.apartment_management_web.service;

import com.me.apartment_management_web.bean.ChartData;

import java.util.List;

public interface IIndexService {

    /**
     * 获取出入分布图表数据
     * @return
     */
    List<ChartData> getAccessChartData();

    /**
     * 获取专业分布图表数据
     * @return
     */
    List<ChartData> getMajorChartData();

    /**
     * 获取公寓人数分布图表数据
     * @return
     */
    List<ChartData> getApartmentChartData();

    /**
     * 获取性别分布图表数据
     * @return
     */
    List<ChartData> getSexChartData();

}
