package com.me.apartment_management_web.task;

import com.me.apartment_management_web.dao.AccessDao;
import com.me.apartment_management_web.dao.AlarmDao;
import com.me.apartment_management_web.entity.Access;
import com.me.apartment_management_web.entity.Alarm;
import com.me.apartment_management_web.enums.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 报警平台定时任务
 */
@Component
public class AlarmTask {

    // 注入dao层实现
    @Resource
    private AccessDao accessDao;

    @Resource
    private AlarmDao alarmDao;

    /**
     * 每1分钟扫描一次t_access表，检测是否有异常行为并记录至t_alarm表
     */
    @Scheduled(fixedRate = 1000*60)
    public void ScanAccess() {

        // 获取全部student_id列表
        List<Access> accessList = accessDao.getAllStudentId();
        List<Integer> studentIdList = new ArrayList<>();
        for (Access item : accessList) {
            int newItem = item.getStudentId();
            studentIdList.add(newItem);
        }

        // 循环查询每个studentId的未被检测过的出入记录
        Map<String, Object> conditionMap = new LinkedHashMap<>();
        Map<String, OrderEnum> orderMap = new LinkedHashMap<>();
        orderMap.put("time", OrderEnum.ASC);
        Access lastAccess = null;

        for (int studentId : studentIdList) {

            lastAccess = null;
            conditionMap.clear();
            conditionMap.put("student_id", studentId);
            conditionMap.put("is_checked", IsCheckedEnum.NOT_CHECKED.getType());
            List<Access> l = accessDao.listByCondition(conditionMap, orderMap);

            // 对每个学生的未被检测的出入记录进行检测，若有异常行为则进行记录
            for (Access item : l) {

                // 若没有上一条出入记录，直接进入下一循环
                if (lastAccess == null) {
                    lastAccess = item;
                    continue;
                }

                // 若有上一条出入记录，则与此条出入记录进行比对，检测是否有异常行为

                // 判断是否“晚出不归”
                // 上一条记录是否是“出”
                boolean condition1 = AccessTypeEnum.OUT.getType().equals(lastAccess.getAccessType());
                // 此条记录是否是“入”
                boolean condition2 = AccessTypeEnum.IN.getType().equals(item.getAccessType());
                // 两条记录的时间是否不在同一天
                long lastTime = lastAccess.getTime().getTime();
                long thisTime = item.getTime().getTime();
                long day1 = lastTime / 86400000;
                long day2 = thisTime / 86400000;
                boolean condition3 = !(day1 == day2);
                // 若三个条件都满足，则判定该学生“晚出不归”
                if (condition1 && condition2 && condition3) {

                    // 记录此次异常行为
                    Alarm alarm = new Alarm();
                    alarm.setStudentId(item.getStudentId());
                    alarm.setAlarmType(AlarmTypeEnum.STAY_OUT.getType());
                    alarm.setIsHandled(IsHandledEnum.NOT_HANDLED.getType());
                    alarm.setStartTime(lastAccess.getTime());
                    alarm.setEndTime(item.getTime());
                    alarm.setCreateTime(new Date());
                    alarm.setModifyTime(new Date());
                    alarmDao.save(alarm);

                }

                // 判断是否“连续一天未出公寓”
                // 上一条记录是否为“入”
                boolean condition4 = AccessTypeEnum.IN.getType().equals(lastAccess.getAccessType());
                // 此条记录是否为“出”
                boolean condition5 = AccessTypeEnum.OUT.getType().equals(item.getAccessType());
                // 两条记录时间间隔是否超过一天
                boolean condition6 = ((thisTime-lastTime)>=86400000);
                // 若三个条件都满足于，则判定该学生“连续一天未出公寓”
                if (condition4 && condition5 && condition6) {

                    // 记录此次异常行为
                    Alarm alarm = new Alarm();
                    alarm.setStudentId(item.getStudentId());
                    alarm.setAlarmType(AlarmTypeEnum.STAY_IN.getType());
                    alarm.setIsHandled(IsHandledEnum.NOT_HANDLED.getType());
                    alarm.setStartTime(lastAccess.getTime());
                    alarm.setEndTime(item.getTime());
                    alarm.setCreateTime(new Date());
                    alarm.setModifyTime(new Date());
                    alarmDao.save(alarm);

                }

                // 将上一条出入记录标记为已检测
                lastAccess.setIsChecked(IsCheckedEnum.IS_CHECKED.getType());
                lastAccess.setModifyTime(new Date());
                accessDao.update(lastAccess);

                // 更改上一条记录为此次记录
                lastAccess = item;

            }

        }

    }

}
