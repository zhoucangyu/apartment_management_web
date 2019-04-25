package com.me.apartment_management_web.service.implement;

import com.me.apartment_management_web.bean.ChartData;
import com.me.apartment_management_web.dao.*;
import com.me.apartment_management_web.entity.*;
import com.me.apartment_management_web.enums.AccessTypeEnum;
import com.me.apartment_management_web.enums.MajorEnum;
import com.me.apartment_management_web.enums.OrderEnum;
import com.me.apartment_management_web.enums.SexEnum;
import com.me.apartment_management_web.service.IIndexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexServiceImpl implements IIndexService {

    // 注入dao层实现
    @Resource
    private BelongDao belongDao;

    @Resource
    private ClassInfoDao classInfoDao;

    @Resource
    private StudentInfoDao studentInfoDao;

    @Resource
    private AccessDao accessDao;

    @Resource
    private ApartmentInfoDao apartmentInfoDao;

    @Resource
    private DormitoryInfoDao dormitoryInfoDao;

    @Resource
    private AccommodationDao accommodationDao;

    @Override
    public List<ChartData> getAccessChartData() {

        // 获取学生ID列表
        List<Access> studentIdList = accessDao.getAllStudentId();

        // 新建公寓内外人数计数器
        int countIn = 0;
        int countOut = 0;

        // 新建查询条件
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        orderMap.put("time", OrderEnum.DESC);

        // 循环查询每个学生的出入情况
        for (Access access : studentIdList) {
            conditionMap.clear();
            conditionMap.put("student_id", access.getStudentId());
            List<Access> accessList = accessDao.listByCondition(conditionMap, orderMap);
            if (accessList != null && !accessList.isEmpty()) {
                if (AccessTypeEnum.IN.getType().equals(accessList.get(0).getAccessType())) {
                    countIn++;
                }
                if (AccessTypeEnum.OUT.getType().equals(accessList.get(0).getAccessType())) {
                    countOut++;
                }
            }
        }

        // 集合数据并插入到list中
        List<ChartData> list = new ArrayList<>();
        ChartData inChartData = new ChartData();
        inChartData.setName(AccessTypeEnum.IN.getState());
        inChartData.setValue(countIn);
        list.add(inChartData);
        ChartData outChartData = new ChartData();
        outChartData.setName(AccessTypeEnum.OUT.getState());
        outChartData.setValue(countOut);
        list.add(outChartData);

        // 返回结果
        return list;

    }

    @Override
    public List<ChartData> getMajorChartData() {

        // 新建返回结果
        List<ChartData> list = new ArrayList<>();

        // 新建搜索条件
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();

        // 循环查询专业学生信息
        for (MajorEnum major : MajorEnum.values()) {
            // 清空查询条件Map
            conditionMap.clear();
            // 在t_class_info表查询专业代码对应的班级
            conditionMap.put("major", major.getType());
            List<ClassInfo> classInfoList = classInfoDao.listByCondition(conditionMap, orderMap);
            // 记录学生人数
            int count = 0;
            // 循环查询班级里对应的学生
            for (ClassInfo classInfo : classInfoList) {
                // 清空查询条件Map
                conditionMap.clear();
                // 在t_belong表查询班级对应的学生
                conditionMap.put("class_id", classInfo.getClassId());
                List<Belong> belongList = belongDao.listByCondition(conditionMap, orderMap);
                count += belongList.size();
            }
            // 集合数据并插入到list中
            ChartData chartData = new ChartData();
            chartData.setName(major.getName());
            chartData.setValue(count);
            list.add(chartData);
        }

        // 返回结果
        return list;

    }

    @Override
    public List<ChartData> getApartmentChartData() {

        // 新建返回结果
        List<ChartData> list = new ArrayList<>();

        // 新建搜索条件
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();

        // 查询所有公寓信息
        List<ApartmentInfo> apartmentList = apartmentInfoDao.listByCondition(conditionMap, orderMap);

        // 循环查询专业学生信息
        for (ApartmentInfo apartmentInfo : apartmentList) {
            // 清空查询条件Map
            conditionMap.clear();
            // 在t_dormitory_info表查询专业代码对应的班级
            conditionMap.put("apartment_id", apartmentInfo.getApartmentId());
            List<DormitoryInfo> dormitoryInfoList = dormitoryInfoDao.listByCondition(conditionMap, orderMap);
            // 记录学生人数
            int count = 0;
            // 循环查询班级里对应的学生
            for (DormitoryInfo dormitoryInfo : dormitoryInfoList) {
                // 清空查询条件Map
                conditionMap.clear();
                // 在t_accommodation表查询班级对应的学生
                conditionMap.put("room_id", dormitoryInfo.getRoomId());
                List<Accommodation> accommodationList = accommodationDao.listByCondition(conditionMap, orderMap);
                count += accommodationList.size();
            }
            // 集合数据并插入到list中
            ChartData chartData = new ChartData();
            chartData.setName(apartmentInfo.getApartmentName());
            chartData.setValue(count);
            list.add(chartData);
        }

        // 返回结果
        return list;

    }

    @Override
    public List<ChartData> getSexChartData() {

        // 新建返回结果
        List<ChartData> list = new ArrayList<>();

        // 新建搜索条件
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();

        // 循环查询学生性别信息
        for (SexEnum sex : SexEnum.values()) {
            // 清空查询条件Map
            conditionMap.clear();
            // 在t_student表查询专业代码对应的班级
            conditionMap.put("sex", sex.getType());
            List<StudentInfo> studentInfoList = studentInfoDao.listByCondition(conditionMap, orderMap);
            // 集合数据并插入到list中
            ChartData chartData = new ChartData();
            chartData.setName(sex.getName());
            chartData.setValue(studentInfoList.size());
            list.add(chartData);
        }

        // 返回结果
        return list;

    }

}
