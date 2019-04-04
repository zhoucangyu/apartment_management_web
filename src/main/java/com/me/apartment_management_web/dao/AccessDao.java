package com.me.apartment_management_web.dao;

import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.bean.RangeCondition;
import com.me.apartment_management_web.entity.Access;
import com.me.apartment_management_web.enums.OrderEnum;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Mapper
public interface AccessDao {

    /**
     * 通过id获取Access实体
     * @param id
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "accessType", column = "access_type"),
            @Result(property = "time", column = "time"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @Select("select * from t_access where id = #{id}")
    Access getById(Integer id);

    /**
     * 插入一条数据库记录
     * @param access
     */
    @Insert("insert into t_access (id, student_id, access_type, time, create_time, modify_time) values (#{id}, #{studentId}, #{accessType}, #{time}, #{createTime}, #{modifyTime})")
    void save(Access access);

    /**
     * 修改一条数据库记录
     * @param access
     */
    @Update("update t_access set student_id = #{studentId}, access_type = #{accessType}, time = #{time}, create_time = #{createTime}, modify_time = #{modifyTime} where id = #{id}")
    void update(Access access);

    /**
     * 删除一条数据库记录
     * @param id
     */
    @Delete("delete from t_access where id = #{id}")
    void delete(Integer id);

    /**
     * 通过条件查询Access实体
     * @param conditionMap
     * @param orderMap
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "accessType", column = "access_type"),
            @Result(property = "time", column = "time"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = AccessDaoProvider.class, method = "listByCondition")
    List<Access> listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap);

    /**
     * 分页查询Access实体
     * @param pageParam
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "accessType", column = "access_type"),
            @Result(property = "time", column = "time"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = AccessDaoProvider.class, method = "listByPage")
    List<Access> listByPage(PageParam pageParam);

    /**
     * 查询符合条件的Access实体总数
     * @param pageParam
     * @return
     */
    @SelectProvider(type = AccessDaoProvider.class, method = "countByPage")
    Integer countByPage(PageParam pageParam);

    /**
     * 查询t_access表中出现的student_id
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "accessType", column = "access_type"),
            @Result(property = "time", column = "time"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @Select("select * from t_access group by student_id")
    List<Access> getAllStudentId();

    /**
     * 动态SQL提供类
     */
    class AccessDaoProvider {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public String listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap) {

            return new SQL() {{
                SELECT("*");
                FROM("t_access");
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
                FROM("t_access");
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
                FROM("t_access");
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
