package com.me.apartment_management_web.controller;

import com.alibaba.fastjson.JSON;
import com.me.apartment_management_web.entity.ClassInfo;
import com.me.apartment_management_web.entity.DormitoryInfo;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.service.IApartmentService;
import com.me.apartment_management_web.service.IClassInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Resource
    private IClassInfoService classInfoService;

    @Resource
    private IApartmentService apartmentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index.html";
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public String data() {

        ClassInfo classInfo = classInfoService.getOneClassInfo();

        return JSON.toJSONString(classInfo);

    }

    @RequestMapping(value = "/dormitory", method = RequestMethod.GET)
    @ResponseBody
    public String dormitory() {
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        conditionMap.put("room_id", 1401);
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        orderMap.put("id", OrderEnum.ASC);
        List<DormitoryInfo> list = apartmentService.getByCondition(conditionMap, orderMap);

        return JSON.toJSONString(list);
    }

}
