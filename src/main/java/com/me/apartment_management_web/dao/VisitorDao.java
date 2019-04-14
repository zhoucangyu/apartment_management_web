package com.me.apartment_management_web.dao;

import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.bean.RangeCondition;
import com.me.apartment_management_web.entity.Visitor;
import com.me.apartment_management_web.enums.OrderEnum;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Mapper
public interface VisitorDao {

    /**
     * 通过id获取Visitor实体
     * @param id
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "visitorType", column = "visitor_type"),
            @Result(property = "idType", column = "id_type"),
            @Result(property = "idNum", column = "id_num"),
            @Result(property = "phoneNum", column = "phone_num"),
            @Result(property = "time", column = "time"),
            @Result(property = "reason", column = "reason"),
            @Result(property = "apartmentId", column = "apartment_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @Select("select * from t_visitor where id = #{id}")
    Visitor getById(Integer id);

    /**
     * 插入一条数据库记录
     * @param visitor
     */
    @Insert("insert into t_visitor (id, name, visitor_type, id_type, id_num, phone_num, time, reason, apartment_id, create_time, modify_time) values (#{id}, #{name}, #{visitorType}, #{idType}, #{idNum}, #{phoneNum}, #{time}, #{reason}, #{apartmentId}, #{createTime}, #{modifyTime})")
    void save(Visitor visitor);

    /**
     * 修改一条数据库记录
     * @param visitor
     */
    @Update("update t_visitor set name = #{name}, visitor_type = #{visitorType}, id_type = #{idType}, id_num = #{idNum}, phone_num = #{phoneNum}, time = #{time}, reason = #{reason}, apartment_id = #{apartmentId}, create_time = #{createTime}, modify_time = #{modifyTime} where id = #{id}")
    void update(Visitor visitor);

    /**
     * 删除一条数据库记录
     * @param id
     */
    @Delete("delete from t_visitor where id = #{id}")
    void delete(Integer id);

    /**
     * 通过条件查询Visitor实体
     * @param conditionMap
     * @param orderMap
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "visitorType", column = "visitor_type"),
            @Result(property = "idType", column = "id_type"),
            @Result(property = "idNum", column = "id_num"),
            @Result(property = "phoneNum", column = "phone_num"),
            @Result(property = "time", column = "time"),
            @Result(property = "reason", column = "reason"),
            @Result(property = "apartmentId", column = "apartment_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = VisitorDaoProvider.class, method = "listByCondition")
    List<Visitor> listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap);

    /**
     * 分页查询Visitor实体
     * @param pageParam
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "visitorType", column = "visitor_type"),
            @Result(property = "idType", column = "id_type"),
            @Result(property = "idNum", column = "id_num"),
            @Result(property = "phoneNum", column = "phone_num"),
            @Result(property = "time", column = "time"),
            @Result(property = "reason", column = "reason"),
            @Result(property = "apartmentId", column = "apartment_id"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = VisitorDaoProvider.class, method = "listByPage")
    List<Visitor> listByPage(PageParam pageParam);

    /**
     * 查询符合条件的Visitor实体总数
     * @param pageParam
     * @return
     */
    @SelectProvider(type = VisitorDaoProvider.class, method = "countByPage")
    Integer countByPage(PageParam pageParam);

    /**
     * 动态SQL提供类
     */
    class VisitorDaoProvider {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public String listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap) {

            return new SQL() {{
                SELECT("*");
                FROM("t_visitor");
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
                FROM("t_visitor");
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
                FROM("t_visitor");
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
