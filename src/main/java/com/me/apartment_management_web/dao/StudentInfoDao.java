package com.me.apartment_management_web.dao;

import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.entity.StudentInfo;
import com.me.apartment_management_web.enums.OrderEnum;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentInfoDao {

    /**
     * 通过id获取StudentInfo实体
     * @param id
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "name", column = "name"),
            @Result(property = "pic", column = "pic"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @Select("select * from t_student_info where id = #{id}")
    StudentInfo getById(Integer id);

    /**
     * 插入一条数据库记录
     * @param studentInfo
     */
    @Insert("insert into t_student_info (id, student_id, sex, name, pic, create_time, modify_time) values (#{id}, #{studentId}, #{sex}, #{name}, #{pic}, #{createTime}, #{modifyTime})")
    void save(StudentInfo studentInfo);

    /**
     * 修改一条数据库记录
     * @param studentInfo
     */
    @Update("update t_student_info set student_id = #{studentId}, sex = #{sex}, name = #{name}, pic = #{pic}, create_time = #{createTime}, modify_time = #{modifyTime} where id = #{id}")
    void update(StudentInfo studentInfo);

    /**
     * 删除一条数据库记录
     * @param id
     */
    @Delete("delete from t_student_info where id = #{id}")
    void delete(Integer id);

    /**
     * 通过条件查询StudentInfo实体
     * @param conditionMap
     * @param orderMap
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "name", column = "name"),
            @Result(property = "pic", column = "pic"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = StudentInfoDaoProvider.class, method = "listByCondition")
    List<StudentInfo> listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap);

    /**
     * 分页查询StudentInfo实体
     * @param pageParam
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "studentId", column = "student_id"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "name", column = "name"),
            @Result(property = "pic", column = "pic"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = StudentInfoDaoProvider.class, method = "listByPage")
    List<StudentInfo> listByPage(PageParam pageParam);

    /**
     * 查询符合条件的StudentInfo实体总数
     * @param pageParam
     * @return
     */
    @SelectProvider(type = StudentInfoDaoProvider.class, method = "countByPage")
    Integer countByPage(PageParam pageParam);

    /**
     * 动态SQL提供类
     */
    class StudentInfoDaoProvider {

        public String listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap) {

            return new SQL() {{
                SELECT("*");
                FROM("t_student_info");
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
                FROM("t_student_info");
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
                FROM("t_student_info");
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
