package com.ling.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpExpr {
    private Integer id; // id
    private Integer empId; // 员工id
    private LocalDate begin; // 开始日期
    private LocalDate end; // 结束日期
    private String company; // 公司名称
    private String job; // 岗位名称
}
