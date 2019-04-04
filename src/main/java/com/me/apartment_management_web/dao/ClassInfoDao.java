package com.me.apartment_management_web.dao;

import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.entity.ClassInfo;
import com.me.apartment_management_web.enums.OrderEnum;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClassInfoDao {

    /**
     * 通过id获取ClassInfo实体
     * @param id
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "classId", column = "class_id"),
            @Result(property = "major", column = "major"),
            @Result(property = "school", column = "school"),
            @Result(property = "teacherName", column = "teacher_name"),
            @Result(property = "teacherPhoneNum", column = "teacher_phone_num"),
            @Result(property = "counselorName", column = "counselor_name"),
            @Result(property = "counselorPhoneNum", column = "counselor_phone_num"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @Select("select * from t_class_info where id = #{id}")
    ClassInfo getById(Integer id);

    /**
     * 插入一条数据库记录
     * @param classInfo
     */
    @Insert("insert into t_class_info (id, class_id, major, school, teacher_name, teacher_phone_num, counselor_name, counselor_phone_num, create_time, modify_time) values (#{id}, #{classId}, #{major}, #{school}, #{teacherName}, #{teacherPhoneNum}, #{counselorName}, #{counselorPhoneNum}, #{createTime}, #{modifyTime})")
    void save(ClassInfo classInfo);

    /**
     * 修改一条数据库记录
     * @param classInfo
     */
    @Update("update t_class_info set class_id = #{classId}, major = #{major}, school = #{school}, teacher_name = #{teacherName}, teacher_phone_num = #{teacherPhoneNum}, counselor_name = #{counselorName}, counselor_phone_num = #{counselorPhoneNum}, create_time = #{createTime}, modify_time = #{modifyTime} where id = #{id}")
    void update(ClassInfo classInfo);

    /**
     * 删除一条数据库记录
     * @param id
     */
    @Delete("delete from t_class_info where id = #{id}")
    void delete(Integer id);

    /**
     * 通过条件查询ClassInfo实体
     * @param conditionMap
     * @param orderMap
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "classId", column = "class_id"),
            @Result(property = "major", column = "major"),
            @Result(property = "school", column = "school"),
            @Result(property = "teacherName", column = "teacher_name"),
            @Result(property = "teacherPhoneNum", column = "teacher_phone_num"),
            @Result(property = "counselorName", column = "counselor_name"),
            @Result(property = "counselorPhoneNum", column = "counselor_phone_num"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = ClassInfoDaoProvider.class, method = "listByCondition")
    List<ClassInfo> listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap);

    /**
     * 分页查询ClassInfo实体
     * @param pageParam
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "classId", column = "class_id"),
            @Result(property = "major", column = "major"),
            @Result(property = "school", column = "school"),
            @Result(property = "teacherName", column = "teacher_name"),
            @Result(property = "teacherPhoneNum", column = "teacher_phone_num"),
            @Result(property = "counselorName", column = "counselor_name"),
            @Result(property = "counselorPhoneNum", column = "counselor_phone_num"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = ClassInfoDaoProvider.class, method = "listByPage")
    List<ClassInfo> listByPage(PageParam pageParam);

    /**
     * 查询符合条件的ClassInfo实体总数
     * @param pageParam
     * @return
     */
    @SelectProvider(type = ClassInfoDaoProvider.class, method = "countByPage")
    Integer countByPage(PageParam pageParam);

    /**
     * 动态SQL提供类
     */
    class ClassInfoDaoProvider {

        public String listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap) {

            return new SQL() {{
                SELECT("*");
                FROM("t_class_info");
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
                FROM("t_class_info");
                if (pageParam != null) {
                    // 从conditionMap取出查询条件
                    if (pageParam.getConditionMap() != null) {
                        for (Map.Entry<String, Object> entry : pageParam.getConditionMap().entrySet()) {
                            WHERE(entry.getKey() + "=" + "\'" + entry.getValue() + "\'");
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
                FROM("t_class_info");
                if (pageParam != null) {
                    if (pageParam.getConditionMap() != null) {
                        for (Map.Entry<String, Object> entry : pageParam.getConditionMap().entrySet()) {
                            WHERE(entry.getKey() + "=" + "\'" + entry.getValue() + "\'");
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
