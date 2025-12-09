package com.ling.service;

import com.ling.pojo.Emp;
import com.ling.pojo.EmpQueryParam;
import com.ling.pojo.PageResult;

import java.util.List;

public interface EmpService {

    //查询员工信息分页列表
    public PageResult<Emp> page(EmpQueryParam empQueryParam);
    //新增员工信息
    public void save(Emp emp);
    //根据id删除员工信息
    public void delete(List<Integer> ids);
    //根据id查询员工信息
    public Emp getInfo(Integer id);
    //更新员工信息
    public void update(Emp emp);
}
