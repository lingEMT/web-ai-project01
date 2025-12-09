package com.ling.config;

import com.ling.intercepter.TokenIntercrpter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类: 注册拦截器
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    //@Autowired
    private TokenIntercrpter tokenIntercrpter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenIntercrpter)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
