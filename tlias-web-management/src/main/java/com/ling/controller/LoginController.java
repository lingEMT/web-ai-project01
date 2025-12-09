package com.ling.controller;

import com.ling.pojo.Emp;
import com.ling.pojo.LoginInfo;
import com.ling.pojo.Result;
import com.ling.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 登录控制器
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody Emp emp) {
        log.info("登录信息: {}, {}", emp.getUsername(), emp.getPassword());
        LoginInfo loginInfo = loginService.login(emp);
        if (loginInfo == null) {
            return Result.error("登录失败");
        }
        return Result.success(loginInfo);
    }
}
