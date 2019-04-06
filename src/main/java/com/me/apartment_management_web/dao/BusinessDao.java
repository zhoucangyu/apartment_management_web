package com.me.apartment_management_web.dao;

import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.bean.RangeCondition;
import com.me.apartment_management_web.entity.Business;
import com.me.apartment_management_web.enums.OrderEnum;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Mapper
public interface BusinessDao {

    /**
     * 通过id获取Business实体
     * @param id
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "businessType", column = "business_type"),
            @Result(property = "description", column = "description"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @Select("select * from t_business where id = #{id}")
    Business getById(Integer id);

    /**
     * 插入一条数据库记录
     * @param business
     */
    @Insert("insert into t_business (id, student_id, business_type, description, start_time, end_time, create_time, modify_time) values (#{id}, #{studentId}, #{businessType}, #{description}, #{startTime}, #{endTime}, #{createTime}, #{modifyTime})")
    void save(Business business);

    /**
     * 修改一条数据库记录
     * @param business
     */
    @Update("update t_business set student_id = #{studentId}, business_type = #{businessType}, description = #{description}, start_time = #{startTime}, end_time = #{endTime}, create_time = #{createTime}, modify_time = #{modifyTime} where id = #{id}")
    void update(Business business);

    /**
     * 删除一条数据库记录
     * @param id
     */
    @Delete("delete from t_business where id = #{id}")
    void delete(Integer id);

    /**
     * 通过条件查询Business实体
     * @param conditionMap
     * @param orderMap
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "businessType", column = "business_type"),
            @Result(property = "description", column = "description"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = BusinessDaoProvider.class, method = "listByCondition")
    List<Business> listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap);

    /**
     * 分页查询Business实体
     * @param pageParam
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "businessType", column = "business_type"),
            @Result(property = "description", column = "description"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = BusinessDaoProvider.class, method = "listByPage")
    List<Business> listByPage(PageParam pageParam);

    /**
     * 查询符合条件的Business实体总数
     * @param pageParam
     * @return
     */
    @SelectProvider(type = BusinessDaoProvider.class, method = "countByPage")
    Integer countByPage(PageParam pageParam);

    /**
     * 动态SQL提供类
     */
    class BusinessDaoProvider {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public String listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap) {

            return new SQL() {{
                SELECT("*");
                FROM("t_business");
                // 从conditionMap里取出查询条件
                if (conditionMap != null) {
                    for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
                        WHERE(entry.getKey() + "=" + "\'" + entry.getValue() + "\'");
                    }
                }
                // 从orderMap里取出排序条件
                if (orderMap != null) {
                    for (Map.Entry<String, OrderEnum> entry : orderMap.entrySet()) {
                        ORDER_BY(entry.getKey() + " " + entry.getValue().name);
                    }
                }
            }}.toString();

        }

        public String listByPage(PageParam pageParam) {
            SQL sql = new SQL() {{
                SELECT("*");
                FROM("t_business");
                if (pageParam != null) {
                    // 从conditionMap取出查询条件
                    if (pageParam.getConditionMap() != null) {
                        for (Map.Entry<String, Object> entry : pageParam.getConditionMap().entrySet()) {
                            WHERE(entry.getKey() + "=" + "\'" + entry.getValue() + "\'");
                        }
                    }

                    // 从rangeMap里取出范围条件
                    if (pageParam.getRangeMap() != null) {
                        for (Map.Entry<String, RangeCondition> entry : pageParam.getRangeMap().entrySet()) {
                            if (entry.getValue().getLowerLimit() != null) {
                                WHERE(entry.getKey() + ">=" + "\'" + format.format(entry.getValue().getLowerLimit()) + "\'");
                            }
                            if (entry.getValue().getUpperLimit() != null) {
                                WHERE(entry.getKey() + "<=" + "\'" + format.format(entry.getValue().getUpperLimit()) + "\'");
                            }
                        }
                    }

                    // 从orderMap取出排序条件
                    if (pageParam.getOrderMap() != null) {
                        for (Map.Entry<String, OrderEnum> entry : pageParam.getOrderMap().entrySet()) {
                            ORDER_BY(entry.getKey() + " " + entry.getValue().name);
                        }
                    }
                }

            }};

            // 从pageNum，pageSize里取出分页条件
            String limit = "";
            if (pageParam != null) {
                limit = " LIMIT " + (pageParam.getPageNum()-1)*pageParam.getPageSize() + ", " + pageParam.getPageSize();
            }

            return sql.toString() + limit;
        }

        public String countByPage(PageParam pageParam) {
            return new SQL() {{
                SELECT("count(*)");
                FROM("t_business");
                if (pageParam != null) {
                    if (pageParam.getConditionMap() != null) {
                        for (Map.Entry<String, Object> entry : pageParam.getConditionMap().entrySet()) {
                            WHERE(entry.getKey() + "=" + "\'" + entry.getValue() + "\'");
                        }
                    }

                    // 从rangeMap里取出范围条件
                    if (pageParam.getRangeMap() != null) {
                        for (Map.Entry<String, RangeCondition> entry : pageParam.getRangeMap().entrySet()) {
                            if (entry.getValue().getLowerLimit() != null) {
                                WHERE(entry.getKey() + ">=" + "\'" + format.format(entry.getValue().getLowerLimit()) + "\'");
                            }
                            if (entry.getValue().getUpperLimit() != null) {
                                WHERE(entry.getKey() + "<=" + "\'" + format.format(entry.getValue().getUpperLimit()) + "\'");
                            }
                        }
                    }

                    if (pageParam.getOrderMap() != null) {
                        for (Map.Entry<String, OrderEnum> entry : pageParam.getOrderMap().entrySet()) {
                            ORDER_BY(entry.getKey() + " " + entry.getValue().name);
                        }
                    }
                }

            }}.toString();
        }
    }

}
