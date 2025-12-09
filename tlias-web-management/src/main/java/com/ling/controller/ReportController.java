package com.ling.controller;

import com.ling.pojo.JobOption;
import com.ling.pojo.Result;
import com.ling.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    //查询员工岗位分布数据
    @GetMapping("/empJobData")
    public Result getEmpJobData() {
        log.info("查询员工岗位分布数据");
        JobOption empJobData = reportService.getEmpJobData();
        return Result.success(empJobData);
    }
    //查询员工性别分布数据
    @GetMapping("/empGenderData")
    public Result getEmpGenderData() {
        log.info("查询员工性别分布数据");
        List<Map<String, Object>> empGenderData = reportService.getEmpGenderData();
        return Result.success(empGenderData);
    }
}
