package com.me.apartment_management_web.controller;

import com.alibaba.fastjson.JSON;
import com.me.apartment_management_web.bean.*;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.service.IApartmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/apartment")
public class ApartmentController {

    // 注入Service层实现
    @Resource
    IApartmentService apartmentService;

    /**
     * 获取所有公寓的名称
     */
    @RequestMapping(value = "/name", method = RequestMethod.GET)
    @ResponseBody
    public String getAllApartmentName() {

        // 查询所有公寓名称
        List<SelectOption> list = apartmentService.getAllApartmentName();

        // 返回结果
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(list);

        return JSON.toJSONString(result);

    }

    /**
     * 查询公寓和宿舍详细信息
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public String getDormitoryDetail(HttpServletRequest request) {

        // 获取前端传来的参数
        String apartmentIdStr = request.getParameter("apartmentId");
        String roomNameStr = request.getParameter("roomName");
        String pageNumStr = request.getParameter("pageNum");
        String pageSizeStr = request.getParameter("pageSize");

        // 转化参数
        int apartmentId = 0;
        if (apartmentIdStr != null && !"".equals(apartmentIdStr)) {
            apartmentId = Integer.parseInt(apartmentIdStr);
        }
        int roomName = 0;
        if (roomNameStr != null && !"".equals(roomNameStr)) {
            roomName = Integer.parseInt(roomNameStr);
        }
        int pageNum = 1;
        if (pageNumStr != null && !"".equals(pageNumStr)) {
            pageNum = Integer.parseInt(pageNumStr);
        }
        int pageSize = 10;
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        // 创建PageParam
        PageParam pageParam = new PageParam();

        // 插入分页查询条件
        pageParam.setPageNum(pageNum);
        pageParam.setPageSize(pageSize);

        Map<String, Object> conditionMap = pageParam.getConditionMap();
        if (apartmentId != 0) {
            conditionMap.put("apartment_id", apartmentId);
        }
        if (roomName != 0) {
            conditionMap.put("room_name", roomName);
        }

        Map<String, OrderEnum> orderMap = pageParam.getOrderMap();
        orderMap.put("room_id", OrderEnum.ASC);

        // 查询公寓和宿舍详细信息
        PageBean<DormitoryDetail> pageBean = apartmentService.getDormitoryDetail(pageParam);

        // 返回结果
        Result result = new Result();
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(pageBean);

        return JSON.toJSONString(result);

    }

    /**
     * 获取宿舍当前住宿情况
     */
    @RequestMapping(value = "/now", method = RequestMethod.GET)
    @ResponseBody
    public String getAccommodationNow(HttpServletRequest request) {

        // 获取前端获取的数据
        String roomIdStr = request.getParameter("roomId");

        // 转化参数
        int roomId = 0;
        if (roomIdStr != null && !"".equals(roomIdStr)) {
            roomId = Integer.parseInt(roomIdStr);
        }

        // 若roomId获取不到，返回错误信息
        Result result = new Result();
        if (roomId == 0) {
            result.setCode(Result.FAILURE);
            result.setMessage("未获取到roomId！");

            return JSON.toJSONString(result);
        }

        // 查询宿舍当前住宿情况
        List<AccommodationDetail> list = apartmentService.getAccommodationNow(roomId);

        // 返回正确结果
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(list);

        return JSON.toJSONString(result);

    }

    /**
     * 获取宿舍历史住宿情况
     */
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ResponseBody
    public String getAccommodationHistory(HttpServletRequest request) {

        // 获取前端获取的数据
        String roomIdStr = request.getParameter("roomId");

        // 转化参数
        int roomId = 0;
        if (roomIdStr != null && !"".equals(roomIdStr)) {
            roomId = Integer.parseInt(roomIdStr);
        }

        // 若roomId获取不到，返回错误信息
        Result result = new Result();
        if (roomId == 0) {
            result.setCode(Result.FAILURE);
            result.setMessage("未获取到roomId！");

            return JSON.toJSONString(result);
        }

        // 查询宿舍当前住宿情况
        List<AccommodationDetail> list = apartmentService.getAccommodationHistory(roomId);

        // 返回正确结果
        result.setCode(Result.SUCCESS);
        result.setMessage("获取成功！");
        result.setData(list);

        return JSON.toJSONString(result);

    }


}
