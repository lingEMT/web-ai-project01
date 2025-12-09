package com.ling.service;

import com.ling.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {

    //查询员工岗位分布数据

    public JobOption getEmpJobData();
    //查询员工性别分布数据
    public List<Map<String, Object>> getEmpGenderData();
}
