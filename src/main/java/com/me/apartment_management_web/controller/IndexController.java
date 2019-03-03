package com.me.apartment_management_web.controller;

import com.alibaba.fastjson.JSON;
import com.me.apartment_management_web.entity.ClassInfo;
import com.me.apartment_management_web.service.IClassInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class IndexController {

    @Resource
    private IClassInfoService classInfoService;

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

}
