package com.me.apartment_management_web.controller;

import com.alibaba.fastjson.JSON;
import com.me.apartment_management_web.bean.*;
import com.me.apartment_management_web.entity.Visitor;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.service.IVisitorService;
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
@RequestMapping("/visitor")
public class VisitorController {

    // 注入Service层实现
    @Resource
    IVisitorService visitorService;

    /**
     * 获取所有访客类型名称
     */
    @RequestMapping(value = "/visitorType", method = RequestMethod.GET)
    @ResponseBody
    public String getVisitorType() {

        // 查询所有公寓名称
        List<SelectOption> list = visitorService.getVisitorType();

        // 返回结果
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(list);

        return JSON.toJSONString(result);

    }

    /**
     * 获取所有证件类型名称
     */
    @RequestMapping(value = "/idType", method = RequestMethod.GET)
    @ResponseBody
    public String getIdType() {

        // 查询所有公寓名称
        List<SelectOption> list = visitorService.getIdType();

        // 返回结果
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(list);

        return JSON.toJSONString(result);

    }

    /**
     * 新增一个访客记录
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addBusiness(HttpServletRequest request) {

        // 获取前端传来的数据
        String nameStr = request.getParameter("name");
        String visitorTypeStr = request.getParameter("visitorType");
        String idTypeStr = request.getParameter("idType");
        String idNumStr = request.getParameter("idNum");
        String phoneNumStr = request.getParameter("phoneNum");
        String timeStr = request.getParameter("time");
        String reasonStr = request.getParameter("reason");
        String apartmentIdStr = request.getParameter("apartmentId");

        // 新建返回结果
        Result result = new Result();

        // 检查传输的数据是否完整
        if (nameStr == null || visitorTypeStr == null || idTypeStr == null || idNumStr == null || phoneNumStr == null || timeStr == null || reasonStr == null || apartmentIdStr == null || "".equals(nameStr) || "".equals(visitorTypeStr) || "".equals(idTypeStr) || "".equals(idNumStr) || "".equals(phoneNumStr) || "".equals(timeStr) || "".equals(reasonStr) || "".equals(apartmentIdStr)) {
            result.setCode(Result.FAILURE);
            result.setMessage("数据传输不完整！");
            return JSON.toJSONString(result);
        }

        // 转化传输过来的数据
        int visitorType = Integer.parseInt(visitorTypeStr);
        int idType = Integer.parseInt(idTypeStr);
        long idNum = Long.parseLong(idNumStr);
        long phoneNum = Long.parseLong(phoneNumStr);
        long timeLong = Long.parseLong(timeStr);
        Date time = new Date(timeLong);
        int apartmentId = Integer.parseInt(apartmentIdStr);

        // 插入数据
        Visitor visitor = new Visitor();
        visitor.setName(nameStr);
        visitor.setVisitorType(visitorType);
        visitor.setIdType(idType);
        visitor.setIdNum(idNum);
        visitor.setPhoneNum(phoneNum);
        visitor.setTime(time);
        visitor.setReason(reasonStr);
        visitor.setApartmentId(apartmentId);
        visitor.setCreateTime(new Date());
        visitor.setModifyTime(new Date());

        // 存储数据
        visitorService.addVisitor(visitor);

        // 返回成功信息
        result.setCode(Result.SUCCESS);
        result.setMessage("添加成功！");

        return JSON.toJSONString(result);

    }

    /**
     * 修改一个访客记录
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String updateBusiness(HttpServletRequest request) {

        // 获取前端传来的数据
        String idStr = request.getParameter("id");
        String nameStr = request.getParameter("name");
        String visitorTypeStr = request.getParameter("visitorType");
        String idTypeStr = request.getParameter("idType");
        String idNumStr = request.getParameter("idNum");
        String phoneNumStr = request.getParameter("phoneNum");
        String timeStr = request.getParameter("time");
        String reasonStr = request.getParameter("reason");
        String apartmentIdStr = request.getParameter("apartmentId");

        // 新建返回结果
        Result result = new Result();

        // 检查传输的数据是否完整
        if (idStr == null || nameStr == null || visitorTypeStr == null || idTypeStr == null || idNumStr == null || phoneNumStr == null || timeStr == null || reasonStr == null || apartmentIdStr == null || "".equals(idStr) || "".equals(nameStr) || "".equals(visitorTypeStr) || "".equals(idTypeStr) || "".equals(idNumStr) || "".equals(phoneNumStr) || "".equals(timeStr) || "".equals(reasonStr) || "".equals(apartmentIdStr)) {
            result.setCode(Result.FAILURE);
            result.setMessage("数据传输不完整！");
            return JSON.toJSONString(result);
        }

        // 转化传输过来的数据
        // 转化传输过来的数据
        int id = Integer.parseInt(idStr);
        int visitorType = Integer.parseInt(visitorTypeStr);
        int idType = Integer.parseInt(idTypeStr);
        long idNum = Long.parseLong(idNumStr);
        long phoneNum = Long.parseLong(phoneNumStr);
        long timeLong = Long.parseLong(timeStr);
        Date time = new Date(timeLong);
        int apartmentId = Integer.parseInt(apartmentIdStr);

        // 查询该id对应的Business实体
        Visitor visitor = visitorService.getById(id);

        // 修改数据
        visitor.setName(nameStr);
        visitor.setVisitorType(visitorType);
        visitor.setIdType(idType);
        visitor.setIdNum(idNum);
        visitor.setPhoneNum(phoneNum);
        visitor.setTime(time);
        visitor.setReason(reasonStr);
        visitor.setApartmentId(apartmentId);
        visitor.setModifyTime(new Date());

        // 存储数据
        visitorService.modifyVisitor(visitor);

        // 返回成功信息
        result.setCode(Result.SUCCESS);
        result.setMessage("修改成功！");

        return JSON.toJSONString(result);

    }

    /**
     * 通过ID获取一个Visitor实体
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
        Visitor visitor = visitorService.getById(id);

        // 返回结果
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(visitor);

        return JSON.toJSONString(result);

    }

    /**
     * 查询访客记录
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public String getVisitorInfo(HttpServletRequest request) {

        // 获取前端传输的数据
        String nameStr = request.getParameter("name");
        String visitorTypeStr = request.getParameter("visitorType");
        String phoneNumStr = request.getParameter("phoneNum");
        String idNumStr = request.getParameter("idNum");
        String idTypeStr = request.getParameter("idType");
        String apartmentIdStr = request.getParameter("apartmentId");
        String timeLowerLimitStr = request.getParameter("timeLowerLimit");
        String timeUpperLimitStr = request.getParameter("timeUpperLimit");
        String pageNumStr = request.getParameter("pageNum");
        String pageSizeStr = request.getParameter("pageSize");

        // 转化前端传来的数据
        String name = null;
        if (nameStr != null && !"".equals(nameStr)) {
            name = nameStr;
        }
        Integer visitorType = null;
        if (visitorTypeStr != null && !"".equals(visitorTypeStr)) {
            visitorType = Integer.parseInt(visitorTypeStr);
        }
        Integer phoneNum = null;
        if (phoneNumStr != null && !"".equals(phoneNumStr)) {
            phoneNum = Integer.parseInt(phoneNumStr);
        }
        Integer idNum = null;
        if (idNumStr != null && !"".equals(idNumStr)) {
            idNum = Integer.parseInt(idNumStr);
        }
        Integer idType = null;
        if (idTypeStr != null && !"".equals(idTypeStr)) {
            idType = Integer.parseInt(idTypeStr);
        }
        Integer apartmentId = null;
        if (apartmentIdStr != null && !"".equals(apartmentIdStr)) {
            apartmentId = Integer.parseInt(apartmentIdStr);
        }
        Date timeLowerLimit = null;
        if (timeLowerLimitStr != null && !"".equals(timeLowerLimitStr)) {
            long timeLowerLimitLong = Long.parseLong(timeLowerLimitStr);
            timeLowerLimit = new Date(timeLowerLimitLong);
        }
        Date timeUpperLimit = null;
        if (timeUpperLimitStr != null && !"".equals(timeUpperLimitStr)) {
            long timeUpperLimitLong = Long.parseLong(timeUpperLimitStr);
            timeUpperLimit = new Date(timeUpperLimitLong);
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
        if (name != null) {
            conditionMap.put("name", name);
        }
        if (visitorType != null) {
            conditionMap.put("visitor_type", visitorType);
        }
        if (phoneNum != null) {
            conditionMap.put("phone_num", phoneNum);
        }
        if (idNum != null) {
            conditionMap.put("id_num", idNum);
        }
        if (idType != null) {
            conditionMap.put("id_type", idType);
        }
        if (apartmentId != null) {
            conditionMap.put("apartment_id", apartmentId);
        }
        if (timeLowerLimit != null && timeUpperLimit != null) {
            RangeCondition rangeCondition = new RangeCondition();
            rangeCondition.setUpperLimit(timeUpperLimit);
            rangeCondition.setLowerLimit(timeLowerLimit);
            rangeMap.put("time", rangeCondition);
        }
        orderMap.put("time", OrderEnum.DESC);
        orderMap.put("modify_time", OrderEnum.DESC);
        orderMap.put("id", OrderEnum.DESC);
        pageParam.setPageNum(pageNum);
        pageParam.setPageSize(pageSize);

        // 查询事务信息
        PageBean<VisitorInfo> pageBean = visitorService.getVisitorInfo(pageParam);

        // 返回成功信息
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(pageBean);

        return JSON.toJSONString(result);

    }

}
