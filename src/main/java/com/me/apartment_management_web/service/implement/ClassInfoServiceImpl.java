package com.me.apartment_management_web.service.implement;

import com.me.apartment_management_web.entity.ClassInfo;
import com.me.apartment_management_web.dao.ClassInfoDao;
import com.me.apartment_management_web.service.IClassInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ClassInfoServiceImpl implements IClassInfoService {

    @Resource
    private ClassInfoDao classInfoDao;

    @Override
    public ClassInfo getOneClassInfo() {
        return classInfoDao.getOneClassInfo();
    }
}
