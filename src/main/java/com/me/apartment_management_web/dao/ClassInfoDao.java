package com.me.apartment_management_web.dao;

import com.me.apartment_management_web.entity.ClassInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClassInfoDao {

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
    @Select("select * from t_class_info where id = 1")
    public ClassInfo getOneClassInfo();

}
