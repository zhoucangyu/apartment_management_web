package com.me.apartment_management_web.dao;

import com.me.apartment_management_web.bean.PageParam;
import com.me.apartment_management_web.entity.ApartmentInfo;
import com.me.apartment_management_web.enums.OrderEnum;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApartmentInfoDao {

    /**
     * 通过id获取ApartmentInfo实体
     * @param id
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "apartmentId", column = "apartment_id"),
            @Result(property = "apartmentName", column = "apartment_name"),
            @Result(property = "principalName", column = "principal_name"),
            @Result(property = "principalPhoneNum", column = "principal_phone_num"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @Select("select * from t_apartment_info where id = #{id}")
    ApartmentInfo getById(Integer id);

    /**
     * 插入一条数据库记录
     * @param apartmentInfo
     */
    @Insert("insert into t_apartment_info (id, apartment_id, apartment_name, principal_name, principal_phone_num, create_time, modify_time) values (#{id}, #{apartmentId}, #{apartmentName}, #{principalName}, #{principalPhoneNum}, #{createTime}, #{modifyTime})")
    void save(ApartmentInfo apartmentInfo);

    /**
     * 修改一条数据库记录
     * @param apartmentInfo
     */
    @Update("update t_apartment_info set apartment_id = #{apartmentId}, apartment_name = #{apartmentName}, principal_name = #{principalName}, principal_phone_num = #{principalPhoneNum}, create_time = #{createTime}, modify_time = #{modifyTime} where id = #{id}")
    void update(ApartmentInfo apartmentInfo);

    /**
     * 删除一条数据库记录
     * @param id
     */
    @Delete("delete from t_apartment_info where id = #{id}")
    void delete(Integer id);

    /**
     * 通过条件查询ApartmentInfo实体
     * @param conditionMap
     * @param orderMap
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "apartmentId", column = "apartment_id"),
            @Result(property = "apartmentName", column = "apartment_name"),
            @Result(property = "principalName", column = "principal_name"),
            @Result(property = "principalPhoneNum", column = "principal_phone_num"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = ApartmentInfoDaoProvider.class, method = "listByCondition")
    List<ApartmentInfo> listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap);

    /**
     * 分页查询ApartmentInfo实体
     * @param pageParam
     * @return
     */
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "apartmentId", column = "apartment_id"),
            @Result(property = "apartmentName", column = "apartment_name"),
            @Result(property = "principalName", column = "principal_name"),
            @Result(property = "principalPhoneNum", column = "principal_phone_num"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "modifyTime", column = "modify_time")
    })
    @SelectProvider(type = ApartmentInfoDaoProvider.class, method = "listByPage")
    List<ApartmentInfo> listByPage(PageParam pageParam);

    /**
     * 查询符合条件的ApartmentInfo实体总数
     * @param pageParam
     * @return
     */
    @SelectProvider(type = ApartmentInfoDaoProvider.class, method = "countByPage")
    Integer countByPage(PageParam pageParam);

    /**
     * 动态SQL提供类
     */
    class ApartmentInfoDaoProvider {

        public String listByCondition(Map<String, Object> conditionMap, Map<String, OrderEnum> orderMap) {

            return new SQL() {{
                SELECT("*");
                FROM("t_apartment_info");
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
                FROM("t_apartment_info");
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
                FROM("t_apartment_info");
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
