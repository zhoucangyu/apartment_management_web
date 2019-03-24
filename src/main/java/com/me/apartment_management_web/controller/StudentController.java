package com.me.apartment_management_web.controller;

import com.alibaba.fastjson.JSON;
import com.me.apartment_management_web.bean.Result;
import com.me.apartment_management_web.bean.StudentDetail;
import com.me.apartment_management_web.service.IStudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

}
