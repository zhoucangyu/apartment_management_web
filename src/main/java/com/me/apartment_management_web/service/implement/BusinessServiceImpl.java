package com.me.apartment_management_web.service.implement;

import com.me.apartment_management_web.bean.BusinessInfo;
import com.me.apartment_management_web.bean.PageBean;
import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.bean.SelectOption;
import com.me.apartment_management_web.dao.BusinessDao;
import com.me.apartment_management_web.dao.StudentInfoDao;
import com.me.apartment_management_web.entity.Business;
import com.me.apartment_management_web.entity.StudentInfo;
import com.me.apartment_management_web.enums.BusinessTypeEnum;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.service.IBusinessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessServiceImpl implements IBusinessService {

    // 注入dao层实现
    @Resource
    private BusinessDao businessDao;

    @Resource
    private StudentInfoDao studentInfoDao;

    @Override
    public List<SelectOption> getBusinessType() {

        // 新建返回结果
        List<SelectOption> list = new ArrayList<>();

        // 循环插入数据
        for (BusinessTypeEnum businessType : BusinessTypeEnum.values()) {
            SelectOption selectOption = new SelectOption();
            selectOption.setKey(businessType.getType());
            selectOption.setValue(businessType.getName());
            list.add(selectOption);
        }

        // 返回结果
        return list;

    }

    @Override
    public void addBusiness(Business business) {
        businessDao.save(business);
    }

    @Override
    public void modifyBusiness(Business business) {
        businessDao.update(business);
    }

    @Override
    public Business getById(Integer id) {
        return businessDao.getById(id);
    }

    @Override
    public PageBean<BusinessInfo> getBusinessInfo(PageParam pageParam) {

        // 分页查询事务信息
        List<Business> list = businessDao.listByPage(pageParam);

        // 查询事务信息总数
        int count = businessDao.countByPage(pageParam);

        // 根据studentId查询studentName并集合数据
        PageBean<BusinessInfo> pageBean = new PageBean<>();
        List<BusinessInfo> newList = new ArrayList<>();

        // 循环向BusinessInfo里插入数据
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        for (Business item : list) {
            BusinessInfo newItem = new BusinessInfo();
            newItem.setId(item.getId());
            newItem.setStudentId(item.getStudentId());
            newItem.setBusinessType(BusinessTypeEnum.getNameByType(item.getBusinessType()));
            newItem.setDescription(item.getDescription());
            newItem.setStartTime(item.getStartTime());
            newItem.setEndTime(item.getEndTime());
            int studentId = item.getStudentId();
            conditionMap.clear();
            conditionMap.put("student_id", studentId);
            List<StudentInfo> l =  studentInfoDao.listByCondition(conditionMap, orderMap);
            newItem.setName(l.get(0).getName());
            newList.add(newItem);
        }

        // 整合成PageBean
        pageBean.setRecordList(newList);
        pageBean.setTotalCount(count);
        pageBean.setPageNum(pageParam.getPageNum());
        pageBean.setPageSize(pageParam.getPageSize());

        // 返回结果
        return pageBean;

    }

}
