package com.ling.mapper;

import com.ling.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DeptMapper {

    // 查询全部部门数据
    @Select("select id, name, create_time, update_time from dept order by update_time desc")
    public List<Dept> findAll();
    // 根据id查询部门信息
    @Select("select * from dept where id = #{id}")
    public Dept getById(Integer id);
    // 根据id删除部门
    @Delete("delete from dept where id = #{id}")
    public void deleteById(Integer id);
    // 更新部门更新时间
    @Update("update dept set update_time = #{current} where id = #{id}")
    public void setCurrentDateTime(Integer id, LocalDateTime current);
    // 根据部门名称添加部门
    @Insert("insert into dept (name, create_time, update_time) values (#{name}, #{createTime}, #{updateTime})")
    public void addByName(Dept dept);
    // 根据id更新部门名称
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    public void updateName(Dept dept);
}
