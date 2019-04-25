package com.me.apartment_management_web.controller;

import com.alibaba.fastjson.JSON;
import com.me.apartment_management_web.bean.Result;
import com.me.apartment_management_web.service.IIndexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class IndexController {

    // 注入Service层实现
    @Resource
    private IIndexService indexService;

    /**
     * 返回首页
     */
    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index.html";
    }

    /**
     * 获取出入分布图表数据
     */
    @RequestMapping(value = "/index/access", method = RequestMethod.GET)
    @ResponseBody
    public String getAccessChartData() {

        // 新建返回结果并插入值
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(indexService.getAccessChartData());

        // 返回结果
        return JSON.toJSONString(result);

    }

    /**
     * 获取专业分布图表数据
     */
    @RequestMapping(value = "/index/major", method = RequestMethod.GET)
    @ResponseBody
    public String getMajorChartData() {

        // 新建返回结果并插入值
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(indexService.getMajorChartData());

        // 返回结果
        return JSON.toJSONString(result);

    }

    /**
     * 获取公寓人数分布图表数据
     */
    @RequestMapping(value = "/index/apartment", method = RequestMethod.GET)
    @ResponseBody
    public String getApartmentChartData() {

        // 新建返回结果并插入值
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(indexService.getApartmentChartData());

        // 返回结果
        return JSON.toJSONString(result);

    }

    /**
     * 获取性别分布图表数据
     */
    @RequestMapping(value = "/index/sex", method = RequestMethod.GET)
    @ResponseBody
    public String getSexChartData() {

        // 新建返回结果并插入值
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(indexService.getSexChartData());

        // 返回结果
        return JSON.toJSONString(result);

    }



}
