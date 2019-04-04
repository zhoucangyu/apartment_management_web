package com.me.apartment_management_web.controller;

import com.alibaba.fastjson.JSON;
import com.me.apartment_management_web.bean.*;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.service.IStudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    // 注入service层实现
    @Resource
    IStudentService studentService;

    /**
     * 获取学生详细信息
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public String getStudentDetail(HttpServletRequest request) {

        // 获取前端传来的studentId参数
        String studentIdStr = request.getParameter("studentId");

        // 转化参数
        int studentId = 0;
        if (studentIdStr != null && !"".equals(studentIdStr)) {
            studentId = Integer.parseInt(studentIdStr);
        }

        // 若studentId获取不到，返回错误信息
        Result result = new Result();
        if (studentId == 0) {
            result.setCode(Result.FAILURE);
            result.setMessage("未获取到studentId");

            return JSON.toJSONString(result);
        }

        // 查询学生详细信息
        StudentDetail studentDetail = studentService.getStudentDetail(studentId);

        // 返回正确结果
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(studentDetail);

        return JSON.toJSONString(result);

    }

    /**
     * 获取学生出入信息
     */
    @RequestMapping(value = "/access", method = RequestMethod.GET)
    @ResponseBody
    public String getAccessInfo(HttpServletRequest request) {

        // 获取前端传来的参数
        String studentIdStr = request.getParameter("studentId");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        String pageNumStr = request.getParameter("pageNum");
        String pageSizeStr = request.getParameter("pageSize");

        // 新建返回结果
        Result result = new Result();

        // 判断参数是否传输正确，否则返回错误信息
        if (studentIdStr == null || "".equals(studentIdStr)) {
            result.setCode(Result.FAILURE);
            result.setMessage("未获取到学号！");
            return JSON.toJSONString(result);
        }

        // 转化参数
        int studentId = Integer.parseInt(studentIdStr);
        int pageNum = 1;
        if (pageNumStr != null && !"".equals(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        int pageSize = 5;
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 创建PageParam
        PageParam pageParam = new PageParam();

        // 插入分页条件
        pageParam.setPageNum(pageNum);
        pageParam.setPageSize(pageSize);

        Map<String, Object> conditionMap = pageParam.getConditionMap();
        conditionMap.put("student_id", studentId);

        Map<String, RangeCondition> rangeMap = pageParam.getRangeMap();
        RangeCondition rangeCondition = new RangeCondition();
        // 若能获取到开始结束时间，就插入条件
        if (startTimeStr != null && !"".equals(startTimeStr)) {
            long startTimeLong = Long.parseLong(startTimeStr);
            Date startTime = new Date(startTimeLong);
            rangeCondition.setLowerLimit(startTime);
        }
        if (endTimeStr != null && !"".equals(endTimeStr)) {
            long endTimeLong = Long.parseLong(endTimeStr);
            Date endTime = new Date(endTimeLong);
            rangeCondition.setUpperLimit(endTime);
        }
        rangeMap.put("time", rangeCondition);

        Map<String, OrderEnum> orderMap = pageParam.getOrderMap();
        orderMap.put("time", OrderEnum.DESC);

        // 查询学生出入信息
        PageBean<AccessInfo> pageBean = studentService.getAccessInfo(pageParam);

        // 返回结果
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(pageBean);

        return JSON.toJSONString(result);

    }

    /**
     * 获取所有学院的名称
     */
    @RequestMapping(value = "/school", method = RequestMethod.GET)
    @ResponseBody
    public String getAllSchool() {

        // 查询所有学院名称
        List<SelectOption> list = studentService.getAllSchool();

        // 返回结果
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(list);

        return JSON.toJSONString(result);

    }

    /**
     * 获取所有专业的名称
     */
    @RequestMapping(value = "/major", method = RequestMethod.GET)
    @ResponseBody
    public String getAllMajor() {

        // 查询所有学院名称
        List<SelectOption> list = studentService.getAllMajor();

        // 返回结果
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(list);

        return JSON.toJSONString(result);

    }

    /**
     * 查询学生详细信息
     */
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    @ResponseBody
    public String getStudentSummary(HttpServletRequest request) {

        // 获取前端传来的参数
        String majorStr = request.getParameter("major");
        String schoolStr = request.getParameter("school");
        String classIdStr = request.getParameter("classId");
        String apartmentIdStr = request.getParameter("apartmentId");
        String roomNameStr = request.getParameter("roomName");
        String stateStr = request.getParameter("state");
        String studentIdStr = request.getParameter("studentId");
        String nameStr = request.getParameter("name");
        String sexStr = request.getParameter("sex");
        String pageNumStr = request.getParameter("pageNum");
        String pageSizeStr = request.getParameter("pageSize");

        // 对前端传来的数据进行转化
        Integer major = null;
        if (majorStr != null && !"".equals(majorStr)) {
            major = Integer.parseInt(majorStr);
        }

        Integer school= null;
        if (schoolStr != null && !"".equals(schoolStr)) {
            school = Integer.parseInt(schoolStr);
        }

        Integer classId = null;
        if (classIdStr != null && !"".equals(classIdStr)) {
            classId = Integer.parseInt(classIdStr);
        }

        Integer apartmentId = null;
        if (apartmentIdStr != null && !"".equals(apartmentIdStr)) {
            apartmentId = Integer.parseInt(apartmentIdStr);
        }

        Integer roomName = null;
        if (roomNameStr != null && !"".equals(roomNameStr)) {
            roomName = Integer.parseInt(roomNameStr);
        }

        Integer state = null;
        if (stateStr != null && !"".equals(stateStr)) {
            state = Integer.parseInt(stateStr);
        }

        Integer studentId = null;
        if (studentIdStr != null && !"".equals(studentIdStr)) {
            studentId = Integer.parseInt(studentIdStr);
        }

        String name = null;
        if (nameStr != null && !"".equals(nameStr)) {
            name = nameStr;
        }

        Integer sex = null;
        if (sexStr != null && !"".equals(sexStr)) {
            sex = Integer.parseInt(sexStr);
        }

        int pageNum = 1;
        if (pageNumStr != null && !"".equals(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }

        int pageSize = 5;
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 查询出符合条件的studentIdList
        List<Integer> studentIdList = studentService.listStudentId(school, major, classId, apartmentId, roomName, state, studentId, name, sex);

        // 通过查询出的studentIdList再去查询相应的studentSummary
        PageBean<StudentSummary> pageBean = studentService.getStudentSummary(studentIdList, pageNum, pageSize);

        // 返回结果
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setData(pageBean);
        result.setMessage("获取成功！");

        return JSON.toJSONString(result);

    }

}
