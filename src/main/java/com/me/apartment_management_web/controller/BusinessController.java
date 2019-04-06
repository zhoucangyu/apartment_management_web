package com.me.apartment_management_web.controller;

import com.alibaba.fastjson.JSON;
import com.me.apartment_management_web.bean.*;
import com.me.apartment_management_web.entity.Business;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.service.IBusinessService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/business")
public class BusinessController {

    // 注入service层实现
    @Resource
    private IBusinessService businessService;

    /**
     * 获取所有事务类型名称
     */
    @RequestMapping(value = "/type", method = RequestMethod.GET)
    @ResponseBody
    public String getBusinessType() {

        // 查询所有公寓名称
        List<SelectOption> list = businessService.getBusinessType();

        // 返回结果
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(list);

        return JSON.toJSONString(result);

    }

    /**
     * 新增一个事务
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addBusiness(HttpServletRequest request) {

        // 获取前端传来的数据
        String studentIdStr = request.getParameter("studentId");
        String businessTypeStr = request.getParameter("businessType");
        String descriptionStr = request.getParameter("description");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");

        // 新建返回结果
        Result result = new Result();

        // 检查传输的数据是否完整
        if (studentIdStr == null || businessTypeStr == null || descriptionStr == null || startTimeStr == null || endTimeStr == null || "".equals(studentIdStr) || "".equals(businessTypeStr) || "".equals(startTimeStr) || "".equals(endTimeStr)) {
            result.setCode(Result.FAILURE);
            result.setMessage("数据传输不完整！");
            return JSON.toJSONString(result);
        }

        // 转化传输过来的数据
        int studentId = Integer.parseInt(studentIdStr);
        int businessType = Integer.parseInt(businessTypeStr);
        long startTimeLong = Long.parseLong(startTimeStr);
        Date startTime = new Date(startTimeLong);
        long endTimeLong = Long.parseLong(endTimeStr);
        Date endTime = new Date(endTimeLong);

        // 插入数据
        Business business = new Business();
        business.setStudentId(studentId);
        business.setBusinessType(businessType);
        business.setDescription(descriptionStr);
        business.setStartTime(startTime);
        business.setEndTime(endTime);
        business.setCreateTime(new Date());
        business.setModifyTime(new Date());

        // 存储数据
        businessService.addBusiness(business);

        // 返回成功信息
        result.setCode(Result.SUCCESS);
        result.setMessage("添加成功！");

        return JSON.toJSONString(result);

    }

    /**
     * 修改一个事物
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateBusiness(HttpServletRequest request) {

        // 获取前端传来的数据
        String idStr = request.getParameter("id");
        String studentIdStr = request.getParameter("studentId");
        String businessTypeStr = request.getParameter("businessType");
        String descriptionStr = request.getParameter("description");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");

        // 新建返回结果
        Result result = new Result();

        // 检查传输的数据是否完整
        if ("".equals(idStr) || "".equals(studentIdStr) || "".equals(businessTypeStr) || "".equals(startTimeStr) || "".equals(endTimeStr)) {
            result.setCode(Result.FAILURE);
            result.setMessage("数据传输不完整！");
            return JSON.toJSONString(result);
        }

        // 转化传输过来的数据
        int id = Integer.parseInt(idStr);
        int studentId = Integer.parseInt(studentIdStr);
        int businessType = Integer.parseInt(businessTypeStr);
        long startTimeLong = Long.parseLong(startTimeStr);
        Date startTime = new Date(startTimeLong);
        long endTimeLong = Long.parseLong(endTimeStr);
        Date endTime = new Date(endTimeLong);

        // 查询该id对应的Business实体
        Business business = businessService.getById(id);

        // 修改数据
        business.setStudentId(studentId);
        business.setBusinessType(businessType);
        business.setDescription(descriptionStr);
        business.setStartTime(startTime);
        business.setEndTime(endTime);
        business.setModifyTime(new Date());

        // 存储数据
        businessService.modifyBusiness(business);

        // 返回成功信息
        result.setCode(Result.SUCCESS);
        result.setMessage("修改成功！");

        return JSON.toJSONString(result);

    }

    /**
     * 通过ID获取一个Business实体
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getById(HttpServletRequest request) {

        // 获取前端传来的数据
        String idStr = request.getParameter("id");

        // 新建返回结果
        Result result = new Result();

        // 检查传输的数据是否完整
        if (idStr == null || "".equals(idStr)) {
            result.setCode(Result.FAILURE);
            result.setMessage("未获取到id！");
            return JSON.toJSONString(result);
        }

        // 转化传输过来的数据
        int id = Integer.parseInt(idStr);

        // 查询该id对应的Business实体
        Business business = businessService.getById(id);

        // 返回结果
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(business);

        return JSON.toJSONString(result);

    }

    /**
     * 查询事务信息
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public String getBusinessInfo(HttpServletRequest request) {

        // 获取前端传输的数据
        String studentIdStr = request.getParameter("studentId");
        String businessTypeStr = request.getParameter("businessType");
        String startTimeUpperLimitStr = request.getParameter("startTimeUpperLimit");
        String startTimeLowerLimitStr = request.getParameter("startTimeLowerLimit");
        String endTimeUpperLimitStr = request.getParameter("endTimeUpperLimit");
        String endTimeLowerLimitStr = request.getParameter("endTimeLowerLimit");
        String pageNumStr = request.getParameter("pageNum");
        String pageSizeStr = request.getParameter("pageSize");

        // 转化前端传来的数据
        Integer studentId = null;
        if (studentIdStr != null && !"".equals(studentIdStr)) {
            studentId = Integer.parseInt(studentIdStr);
        }
        Integer businessType = null;
        if (businessTypeStr != null && !"".equals(businessTypeStr)) {
            businessType = Integer.parseInt(businessTypeStr);
        }
        Date startTimeUpperLimit = null;
        if (startTimeUpperLimitStr != null && !"".equals(startTimeUpperLimitStr)) {
            long startTimeUpperLimitLong = Long.parseLong(startTimeUpperLimitStr);
            startTimeUpperLimit = new Date(startTimeUpperLimitLong);
        }
        Date startTimeLowerLimit = null;
        if (startTimeLowerLimitStr != null && !"".equals(startTimeLowerLimitStr)) {
            long startTimeLowerLimitLong = Long.parseLong(startTimeLowerLimitStr);
            startTimeLowerLimit = new Date(startTimeLowerLimitLong);
        }
        Date endTimeUpperLimit = null;
        if (endTimeUpperLimitStr != null && !"".equals(endTimeUpperLimitStr)) {
            long endTimeUpperLimitLong = Long.parseLong(endTimeUpperLimitStr);
            endTimeUpperLimit = new Date(endTimeUpperLimitLong);
        }
        Date endTimeLowerLimit = null;
        if (endTimeLowerLimitStr != null && !"".equals(endTimeLowerLimitStr)) {
            long endTimeLowerLimitLong = Long.parseLong(endTimeLowerLimitStr);
            endTimeLowerLimit = new Date(endTimeLowerLimitLong);
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
        Map<String, RangeCondition> rangeMap = pageParam.getRangeMap();
        if (studentId != null) {
            conditionMap.put("student_id", studentId);
        }
        if (businessType != null) {
            conditionMap.put("business_type", businessType);
        }
        if (startTimeLowerLimit != null && startTimeUpperLimit != null) {
            RangeCondition rangeCondition = new RangeCondition();
            rangeCondition.setUpperLimit(startTimeUpperLimit);
            rangeCondition.setLowerLimit(startTimeLowerLimit);
            rangeMap.put("start_time", rangeCondition);
        }
        if (endTimeLowerLimit != null && endTimeUpperLimit != null) {
            RangeCondition rangeCondition = new RangeCondition();
            rangeCondition.setUpperLimit(endTimeUpperLimit);
            rangeCondition.setLowerLimit(endTimeLowerLimit);
            rangeMap.put("end_time", rangeCondition);
        }
        orderMap.put("modify_time", OrderEnum.DESC);
        orderMap.put("id", OrderEnum.DESC);
        pageParam.setPageNum(pageNum);
        pageParam.setPageSize(pageSize);

        // 查询事务信息
        PageBean<BusinessInfo> pageBean = businessService.getBusinessInfo(pageParam);

        // 返回成功信息
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(pageBean);

        return JSON.toJSONString(result);

    }

}
