package com.ling.service;

import com.ling.pojo.Dept;

import java.util.List;

public interface DeptService {

    //查询所有部门信息
    List<Dept> findAll();
    //根据id查询部门信息
    Dept getInfo(Integer id);
    //根据id删除部门信息
    void deleteById(Integer id);
    //根据id设置部门的创建时间和更新时间为当前时间
    void setCurrentDateTime(Integer id);
    //根据部门名称新增部门信息
    void addByName(Dept dept);
    //根据部门id更新部门名称
    void updateName(Dept dept);
}
