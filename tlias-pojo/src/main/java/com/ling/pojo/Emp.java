package com.ling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emp {
    private Integer id; // id
    private String username; // 用户名
    private String password; // 密码
    private String name; // 姓名
    private Integer gender; // 性别
    private String phone; // 手机号
    private Integer job; // 岗位
    private Integer salary; // 薪资
    private String image; // 头像
    private LocalDate entryDate; // 入职日期
    private Integer deptId; // 部门id
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 更新时间

    private String deptName; // 部门名称
    private List<EmpExpr> exprList; // 工作经历表
}
