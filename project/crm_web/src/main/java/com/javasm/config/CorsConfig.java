package com.javasm.config;

import com.javasm.commons.entity.AppProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:SG
 * @classname: CorsConfig
 * @description:
 * @date: 2022/9/19 19:29
 * @version:0.1
 * @since:jdk11
 */
@Configuration
public class CorsConfig {
    @Resource
    private AppProperties cp;

    @Bean
    public FilterRegistrationBean corsFilter(){
        AppProperties.Cors cors = cp.getCors();
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        Map<String, CorsConfiguration> map = new HashMap<>();
        CorsConfiguration corsConfiguration=  new CorsConfiguration();
        if(cors.getHeaders().size()>0){
            corsConfiguration.setExposedHeaders(cors.getHeaders());
        }else{
            corsConfiguration.addAllowedHeader("*");
        }
        if(cors.getMethods().size()>0){
            corsConfiguration.setAllowedMethods(cors.getMethods());
        }else{
            corsConfiguration.addAllowedMethod("*");
        }

        corsConfiguration.setAllowedOrigins(cors.getOrigins());

        corsConfiguration.setAllowCredentials(cors.getCredentials());
        if(cors.getExposedHeaders().size()>0){
            corsConfiguration.setExposedHeaders(cors.getExposedHeaders());
        }
        corsConfiguration.setMaxAge(cors.getMaxAge());
        map.put(cors.getPath(),corsConfiguration);
        corsConfigurationSource.setCorsConfigurations(map);
        FilterRegistrationBean f = new FilterRegistrationBean(new CorsFilter(corsConfigurationSource));
        f.setOrder(1);
        return f;
    }
}
