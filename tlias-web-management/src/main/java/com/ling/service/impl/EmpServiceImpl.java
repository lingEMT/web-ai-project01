package com.ling.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ling.mapper.EmpMapper;
import com.ling.pojo.Emp;
import com.ling.pojo.EmpQueryParam;
import com.ling.pojo.PageResult;
import com.ling.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        // 分页查询 每页显示pageSize条记录, 当前是第page页
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        List<Emp> emplist = empMapper.listByCondition(empQueryParam);
        // 从分页结果中获取分页信息, 包括总记录数和当前页数据
        Page<Emp> p = (Page<Emp>) emplist;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }

    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    @Override
    public void save(Emp emp) {
        // 新增员工信息
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insertEmp(emp);
        // 新增员工工作经历信息
        if(emp.getExprList() != null && !emp.getExprList().isEmpty()){
            emp.getExprList().forEach(item -> item.setEmpId(emp.getId()));
            empMapper.insertEmpExpr(emp.getExprList());
        }
    }

    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED) // 开启事务, 回滚异常, 传播行为为REQUIRED
    @Override
    public void delete(List<Integer> ids) {
        // 根据id删除员工信息
        empMapper.deleteByIds(ids);
        // 根据员工id删除员工工作经历信息
        empMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Override
    public void update(Emp emp) {
        // 更新员工信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        // 更新员工工作经历信息
        if(emp.getExprList() != null && !emp.getExprList().isEmpty()){
            emp.getExprList().forEach(item -> item.setEmpId(emp.getId()));
            empMapper.insertEmpExpr(emp.getExprList());
        }
    }
}
