package com.ling.service.impl;

import com.ling.mapper.EmpMapper;
import com.ling.pojo.JobOption;
import com.ling.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;


    @Override
    public JobOption getEmpJobData() {
        //查询员工岗位分布数据
        List<Map<String, Object>> list = empMapper.countEmpJobData();
        //将查询结果转换为JobOption对象
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("num")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        //查询员工性别分布数据
        return empMapper.countEmpGenderData();
    }
}
