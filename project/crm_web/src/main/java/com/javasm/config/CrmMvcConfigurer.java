package com.javasm.config;

import com.javasm.interceptors.LoginInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author:SG
 * @classname: CrmMvcConfigurer
 * @description:
 * @date: 2022/9/19 10:49
 * @version:0.1
 * @since:jdk11
 */
@Component
public class CrmMvcConfigurer implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor lg;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(lg).addPathPatterns("/**").excludePathPatterns("/loginByPwd","/valicode/**");

    }
}
