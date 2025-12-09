package com.ling.intercepter;

import com.ling.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截器: 校验token
 */
@Slf4j
//@Component
public class TokenIntercrpter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求路径
        String uri = request.getRequestURI();
        //获取请求头中的token
        String token = request.getHeader("token");
        if(token == null || token.isEmpty()){
            log.error("请求路径: {}, 未携带token", uri);
            response.setStatus(401);
            return false;
        }
        //解析token

        try {
            JwtUtils.parseToken(token);
        } catch (Exception e) {
            log.error("请求路径: {}, token解析失败", uri);
            response.setStatus(401);
            return false;
        }
        //继续执行后续拦截器
        return true;
    }
}
