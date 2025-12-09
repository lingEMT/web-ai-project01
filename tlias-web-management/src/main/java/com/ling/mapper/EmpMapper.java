package com.ling.mapper;

import com.ling.pojo.Emp;
import com.ling.pojo.EmpExpr;
import com.ling.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    //查询所有员工信息, 并关联查询部门名称
    public List<Emp> list();
    //根据条件查询员工信息, 并关联查询部门名称
    public List<Emp> listByCondition(EmpQueryParam empQueryParam);
    //新增员工信息, 并返回新增的id
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values(#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    public void insertEmp(Emp emp);
    //新增员工工作经历信息
    public void insertEmpExpr(List<EmpExpr> exprList);
    //根据id删除员工信息
    public void deleteByIds(List<Integer> ids);
    //根据员工id删除员工工作经历信息
    public void deleteByEmpIds(List<Integer> ids);
    //根据id查询员工信息, 并关联查询员工工作经历信息
    public Emp getById(Integer id);
    //更新员工信息
    public void updateById(Emp emp);
    //查询员工岗位分布数据，返回岗位名称和员工数量
    public List<Map<String, Object>> countEmpJobData();
    //查询员工性别分布数据，返回性别名称和员工数量
    public List<Map<String, Object>> countEmpGenderData();
    //根据username，password查询员工信息
    @Select("select id, username, password from emp where username = #{username} and password = #{password}")
    public Emp getByUsernameAndPassword(Emp emp);
}
