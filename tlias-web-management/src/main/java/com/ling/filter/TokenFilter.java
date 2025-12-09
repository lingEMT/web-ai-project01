package com.ling.filter;

import com.ling.utils.CurrentHolder;
import com.ling.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 过滤器: 校验token
 */
@Slf4j
@WebFilter("/*")
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //转换为HttpServletRequest和HttpServletResponse
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取请求路径
        String uri = request.getRequestURI();
        //判断是否是登录请求或静态资源
        if(uri.contains("/login")){
            filterChain.doFilter(request, response);
            return;
        }
        //获取请求头中的token
        String token = request.getHeader("token");
        if(token == null || token.isEmpty()){
            log.error("请求路径: {}, 未携带token", uri);
            response.setStatus(401);
            return;
        }
        //解析token
        try {
            Claims claims = JwtUtils.parseToken(token);
            Integer id = (Integer) claims.get("id");
            CurrentHolder.setCurrent(id);
        } catch (Exception e) {
            log.error("请求路径: {}, token解析失败", uri);
            response.setStatus(401);
            return;
        }
        //继续执行后续过滤器
        filterChain.doFilter(request, response);
    }

}