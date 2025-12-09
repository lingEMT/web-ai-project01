package com.ling.service.impl;

import com.ling.mapper.EmpMapper;
import com.ling.pojo.Emp;
import com.ling.pojo.LoginInfo;
import com.ling.service.LoginService;
import com.ling.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public LoginInfo login(Emp emp) {
        log.info("登录验证: 用户名={}, 密码={}", emp.getUsername(), emp.getPassword());
        Emp loginEmp = empMapper.getByUsernameAndPassword(emp);
        if (loginEmp == null) {
            log.info("登录失败: 用户名或密码错误");
            return null;
        }
        // 生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginEmp.getId());
        claims.put("username", loginEmp.getUsername());
        String token = JwtUtils.generateToken(claims);
        log.info("登录成功: 用户ID={}, Token={}", loginEmp.getId(), token);
        return new LoginInfo(loginEmp.getId(), loginEmp.getUsername(), loginEmp.getPassword(), token);
    }
}