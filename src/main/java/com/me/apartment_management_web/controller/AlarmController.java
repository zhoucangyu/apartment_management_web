package com.me.apartment_management_web.controller;

import com.alibaba.fastjson.JSON;
import com.me.apartment_management_web.bean.*;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.service.IAlarmService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/alarm")
public class AlarmController {

    // 注入Service层实现
    @Resource
    private IAlarmService alarmService;

    /**
     * 获取所有报警类型
     */
    @RequestMapping(value = "/alarmType", method = RequestMethod.GET)
    @ResponseBody
    public String getAlarmType() {

        // 查询所有公寓名称
        List<SelectOption> list = alarmService.getAlarmType();

        // 返回结果
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(list);

        return JSON.toJSONString(result);

    }

    /**
     * 获取是否已被处理过
     */
    @RequestMapping(value = "/isHandled", method = RequestMethod.GET)
    @ResponseBody
    public String getIsHandled() {

        // 查询所有公寓名称
        List<SelectOption> list = alarmService.getIsHandled();

        // 返回结果
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(list);

        return JSON.toJSONString(result);

    }

    /**
     *
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public String getAlarmInfo(HttpServletRequest request) {

        // 获取前端传输的数据
        String studentIdStr = request.getParameter("studentId");
        String alarmTypeStr = request.getParameter("alarmType");
        String isHandledStr = request.getParameter("isHandled");
        String pageNumStr = request.getParameter("pageNum");
        String pageSizeStr = request.getParameter("pageSize");

        // 转化前端传来的数据
        Integer studentId = null;
        if (studentIdStr != null && !"".equals(studentIdStr)) {
            studentId = Integer.parseInt(studentIdStr);
        }
        Integer alarmType = null;
        if (alarmTypeStr != null && !"".equals(alarmTypeStr)) {
            alarmType = Integer.parseInt(alarmTypeStr);
        }
        Integer isHandled = null;
        if (isHandledStr != null && !"".equals(isHandledStr)) {
            isHandled = Integer.parseInt(isHandledStr);
        }
        int pageNum = 1;
        if (pageNumStr != null && !"".equals(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        int pageSize = 5;
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 转化成为分页条件
        PageParam pageParam = new PageParam();
        Map<String, Object> conditionMap = pageParam.getConditionMap();
        Map<String, OrderEnum> orderMap = pageParam.getOrderMap();
        if (studentId != null) {
            conditionMap.put("student_id", studentId);
        }
        if (alarmType != null) {
            conditionMap.put("alarm_type", alarmType);
        }
        if (isHandled != null) {
            conditionMap.put("is_handled", isHandled);
        }
        orderMap.put("is_handled", OrderEnum.ASC);
        orderMap.put("end_time", OrderEnum.DESC);
        orderMap.put("id", OrderEnum.DESC);
        pageParam.setPageNum(pageNum);
        pageParam.setPageSize(pageSize);

        // 查询报警信息
        PageBean<AlarmInfo> pageBean = alarmService.getAlarmInfo(pageParam);

        // 返回成功信息
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(pageBean);

        return JSON.toJSONString(result);

    }

    /**
     * 处理对应id的报警信息
     */
    @RequestMapping(value = "/handle", method = RequestMethod.POST)
    @ResponseBody
    public String  handleAlarm(HttpServletRequest request) {

        // 获取前端传输的数据
        String idStr = request.getParameter("id");

        // 新建返回结果
        Result result = new Result();

        // 若未获取到id，返回错误信息
        if (idStr == null || "".equals(idStr)) {
            result.setCode(Result.FAILURE);
            result.setMessage("未获取到id！");

            return JSON.toJSONString(result);
        }

        // 转化前端传输的数据
        int id = Integer.parseInt(idStr);

        // 尝试处理该id对应的报警信息
        boolean isSuccess = alarmService.handleAlarmById(id);

        // 若方法返回false，则返回错误信息
        if (!isSuccess) {
            result.setCode(Result.FAILURE);
            result.setMessage("处理该报警信息失败！");

            return JSON.toJSONString(result);
        }

        // 若返回true，则返回正确信息
        result.setCode(Result.SUCCESS);
        result.setMessage("处理成功！");

        return JSON.toJSONString(result);

    }

}
