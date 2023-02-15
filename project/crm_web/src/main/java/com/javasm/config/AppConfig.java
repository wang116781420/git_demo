package com.javasm.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.UnknownHostException;

/**
 * @author:SG
 * @classname: AppConfig
 * @description:
 * @date: 2022/9/19 10:05
 * @version:0.1
 * @since:jdk11
 */
@Configuration
@MapperScan("com.javasm.*.dao")
@EnableTransactionManagement//事务注解识别，还需在方法上注解事务注解
@EnableAspectJAutoProxy

public class AppConfig {

    @Bean(name="redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
