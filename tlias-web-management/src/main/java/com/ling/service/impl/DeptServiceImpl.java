package com.ling.service.impl;

import com.ling.mapper.DeptMapper;
import com.ling.pojo.Dept;
import com.ling.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public Dept getInfo(Integer id) {
        return deptMapper.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    @Override
    public void setCurrentDateTime(Integer id) {
        deptMapper.setCurrentDateTime(id, LocalDateTime.now());
    }

    @Override
    public void addByName(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.addByName(dept);
    }

    @Override
    public void updateName(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateName(dept);
    }
}
