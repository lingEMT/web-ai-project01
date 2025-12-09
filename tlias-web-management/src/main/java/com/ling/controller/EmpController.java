package com.ling.controller;

import com.ling.anno.Log;
import com.ling.pojo.Emp;
import com.ling.pojo.EmpQueryParam;
import com.ling.pojo.PageResult;
import com.ling.pojo.Result;
import com.ling.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    //分页查询员工信息
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询, {}", empQueryParam);
        PageResult<Emp> pr = empService.page(empQueryParam);
        return Result.success(pr);
    }
    //新增员工信息
    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("新增员工, {}", emp);
        empService.save(emp);
        return Result.success();
    }
    //根据id删除员工信息
    @Log
    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids){
        log.info("根据id删除员工, {}", ids);
        empService.delete(ids);
        return Result.success();
    }
    //根据id查询员工信息, 并关联查询员工工作经历信息
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询员工信息, {}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }
    //更新员工信息
    @Log
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("更新员工信息, {}", emp);
        empService.update(emp);
        return Result.success();
    }
}
