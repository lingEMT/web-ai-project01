package com.ling.controller;

import com.ling.anno.Log;
import com.ling.pojo.Dept;
import com.ling.pojo.Result;
import com.ling.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门控制器
 * 处理部门相关的请求，如查询、删除、添加、更新部门信息
 */
@Slf4j // 日志记录
@RequestMapping("/depts") // 部门相关请求路径
@RestController
public class DeptController {

    //注入部门服务
    @Autowired
    private DeptService deptService;

    //@RequestMapping(value = "/depts",method = RequestMethod.GET)
    //查询全部部门数据
    @GetMapping
    public Result list(){
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }
    //根据id查询部门
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        Dept byId = deptService.getInfo(id);
        log.info("根据id查询部门：{}", id);
        return Result.success(byId);
    }
    //删除部门
    @Log
    @DeleteMapping
    public Result deleteById(@RequestParam(value = "id",required = false) Integer id){
        deptService.deleteById(id);
        log.info("根据id删除部门：{}", id);
        return Result.success();
    }
    //添加部门
    @Log
    @PostMapping
    public Result addtByName(@RequestBody Dept dept){
        deptService.addByName(dept);
        log.info("已添加部门：{}", dept.getName());
        return Result.success();
    }
    //更新部门名称
    @Log
    @PutMapping
    public Result updateName(@RequestBody Dept dept){
        deptService.updateName(dept);
        log.info("更新部门：{}，名称为：{}", dept.getId(), dept.getName());
        return Result.success();
    }
}
