package com.me.apartment_management_web.controller;

import com.alibaba.fastjson.JSON;
import com.me.apartment_management_web.bean.Result;
import com.me.apartment_management_web.service.IIndexService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    /**
     * 获取当前登录的用户名
     */
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String getUsername() {

        // 获取当前用户信息
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        // 获取username
        String username = userDetails.getUsername();

        // 新建返回结果
        Result result = new Result();

        // 若获取到的结果为空，返回失败信息
        if (username == null || "".equals(username)) {
            result.setCode(Result.FAILURE);
            result.setMessage("获取当前用户名失败！");
            return JSON.toJSONString(result);
        }

        // 否则返回成功信息
        result.setCode(Result.SUCCESS);
        result.setData(username);
        result.setMessage("获取当前用户名成功！");

        return JSON.toJSONString(result);

    }



}
