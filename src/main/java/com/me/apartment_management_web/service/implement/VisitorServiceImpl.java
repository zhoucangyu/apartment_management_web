package com.me.apartment_management_web.service.implement;

import com.me.apartment_management_web.bean.PageBean;
import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.bean.SelectOption;
import com.me.apartment_management_web.bean.VisitorInfo;
import com.me.apartment_management_web.dao.ApartmentInfoDao;
import com.me.apartment_management_web.dao.VisitorDao;
import com.me.apartment_management_web.entity.ApartmentInfo;
import com.me.apartment_management_web.entity.Visitor;
import com.me.apartment_management_web.enums.IdTypeEnum;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.enums.VisitorTypeEnum;
import com.me.apartment_management_web.service.IVisitorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitorServiceImpl implements IVisitorService {

    // 注入dao层实现
    @Resource
    VisitorDao visitorDao;

    @Resource
    ApartmentInfoDao apartmentInfoDao;

    @Override
    public List<SelectOption> getVisitorType() {

        // 新建返回结果
        List<SelectOption> list = new ArrayList<>();

        // 循环插入数据
        for (VisitorTypeEnum visitorType : VisitorTypeEnum.values()) {
            SelectOption selectOption = new SelectOption();
            selectOption.setKey(visitorType.getType());
            selectOption.setValue(visitorType.getName());
            list.add(selectOption);
        }

        // 返回结果
        return list;

    }

    @Override
    public List<SelectOption> getIdType() {

        // 新建返回结果
        List<SelectOption> list = new ArrayList<>();

        // 循环插入数据
        for (IdTypeEnum idType : IdTypeEnum.values()) {
            SelectOption selectOption = new SelectOption();
            selectOption.setKey(idType.getType());
            selectOption.setValue(idType.getName());
            list.add(selectOption);
        }

        // 返回结果
        return list;

    }

    @Override
    public void addVisitor(Visitor visitor) {
        visitorDao.save(visitor);
    }

    @Override
    public void modifyVisitor(Visitor visitor) {
        visitorDao.update(visitor);
    }

    @Override
    public Visitor getById(Integer id) {
        return visitorDao.getById(id);
    }

    @Override
    public PageBean<VisitorInfo> getVisitorInfo(PageParam pageParam) {

        // 分页查询事务信息
        List<Visitor> list = visitorDao.listByPage(pageParam);

        // 查询事务信息总数
        int count = visitorDao.countByPage(pageParam);

        // 根据apartmentId查询apartmentName并集合数据
        PageBean<VisitorInfo> pageBean = new PageBean<>();
        List<VisitorInfo> newList = new ArrayList<>();

        // 循环向VisitorInfo里插入数据
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        for (Visitor item : list) {
            VisitorInfo newItem = new VisitorInfo();
            newItem.setId(item.getId());
            newItem.setName(item.getName());
            newItem.setIdNum(item.getIdNum());
            newItem.setPhoneNum(item.getPhoneNum());
            newItem.setTime(item.getTime());
            newItem.setReason(item.getReason());
            newItem.setVisitorType(VisitorTypeEnum.getNameByType(item.getVisitorType()));
            newItem.setIdType(IdTypeEnum.getNameByType(item.getIdType()));
            int apartmentId = item.getApartmentId();
            conditionMap.clear();
            conditionMap.put("apartment_id", apartmentId);
            List<ApartmentInfo> l = apartmentInfoDao.listByCondition(conditionMap, orderMap);
            newItem.setApartmentName(l.get(0).getApartmentName());
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
