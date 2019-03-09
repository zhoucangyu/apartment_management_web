package com.me.apartment_management_web.dao;

import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.entity.Accommodation;
import com.me.apartment_management_web.enums.OrderEnum;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccommodationDao {

    /**
     * 通过id获取Accommodation实体
     * @param id
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "roomId", column = "room_id"),
            @Result(property = "moveInTime", column = "move_in_time"),
            @Result(property = "moveOutTime", column = "move_out_time"),
            @Result(property = "isIn", column = "is_in"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @Select("select * from t_accommodation where id = #{id}")
    Accommodation getById(Integer id);

    /**
     * 插入一条数据库记录
     * @param accommodation
     */
    @Insert("insert into t_accommodation (id, student_id, room_id, move_in_time, move_out_time, is_in, create_time, modify_time) values (#{id}, #{studentId}, #{roomId}, #{moveInTime}, #{moveOutTime}, #{isIn}, #{createTime}, #{modifyTime})")
    void save(Accommodation accommodation);

    /**
     * 修改一条数据库记录
     * @param accommodation
     */
    @Update("update t_accommodation set student_id = #{studentId}, room_id = #{roomId}, move_in_time = #{moveInTime}, move_out_time = #{move_out_time}, is_in = #{isIn}, create_time = #{createTime}, modify_time = #{modifyTime} where id = #{id}")
    void update(Accommodation accommodation);

    /**
     * 删除一条数据库记录
     * @param id
     */
    @Delete("delete from t_accommodation where id = #{id}")
    void delete(Integer id);

    /**
     * 通过条件查询Accommodation实体
     * @param conditionMap
     * @param orderMap
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "roomId", column = "room_id"),
            @Result(property = "moveInTime", column = "move_in_time"),
            @Result(property = "moveOutTime", column = "move_out_time"),
            @Result(property = "isIn", column = "is_in"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = AccommodationDaoProvider.class, method = "listByCondition")
    List<Accommodation> listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap);

    /**
     * 分页查询Accommodation实体
     * @param pageParam
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "roomId", column = "room_id"),
            @Result(property = "moveInTime", column = "move_in_time"),
            @Result(property = "moveOutTime", column = "move_out_time"),
            @Result(property = "isIn", column = "is_in"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = AccommodationDaoProvider.class, method = "listByPage")
    List<Accommodation> listByPage(PageParam pageParam);

    /**
     * 查询符合条件的Accommodation实体总数
     * @param pageParam
     * @return
     */
    @SelectProvider(type = AccommodationDaoProvider.class, method = "countByPage")
    Integer countByPage(PageParam pageParam);

    /**
     * 动态SQL提供类
     */
    class AccommodationDaoProvider {

        public String listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap) {

            return new SQL() {{
                SELECT("*");
                FROM("t_accommodation");
                // 从conditionMap里取出查询条件
                if (conditionMap != null) {
                    for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
                        WHERE(entry.getKey() + "=" + entry.getValue());
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
                FROM("t_accommodation");
                if (pageParam != null) {
                    // 从conditionMap取出查询条件
                    if (pageParam.getConditionMap() != null) {
                        for (Map.Entry<String, Object> entry : pageParam.getConditionMap().entrySet()) {
                            WHERE(entry.getKey() + "=" + entry.getValue());
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
                FROM("t_accommodation");
                if (pageParam != null) {
                    if (pageParam.getConditionMap() != null) {
                        for (Map.Entry<String, Object> entry : pageParam.getConditionMap().entrySet()) {
                            WHERE(entry.getKey() + "=" + entry.getValue());
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
