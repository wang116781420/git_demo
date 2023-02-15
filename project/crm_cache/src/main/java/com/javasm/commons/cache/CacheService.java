package com.javasm.commons.cache;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {

    @Resource
    private RedisTemplate<String,Object> rt;

    public void set(String key,Object value){
        ValueOperations<String, Object> vo = rt.opsForValue();
        vo.set(key,value);
    }

    public void set(String key, Object value, Integer t, TimeUnit tu){
        ValueOperations<String, Object> vo = rt.opsForValue();
        vo.set(key,value,t,tu);
    }

    public Object get(String key) {
        ValueOperations<String, Object> vo = rt.opsForValue();
        Object o = vo.get(key);
        return o;
    }

    public void setnx(String key, Object value){
        ValueOperations<String, Object> vo = rt.opsForValue();
        vo.setIfAbsent(key,value);
    }

    public void del(String key){
        rt.delete(key);
    }

    public void hset(String key,Object field,Object value){
        HashOperations<String, Object, Object> ho = rt.opsForHash();
        ho.put(key,field,value);
    }

    public void lpush(String key, Collection itemList) {
        ListOperations<String, Object> lo = rt.opsForList();
        lo.leftPushAll(key,itemList);
    }

    public List getList(String key) {
        ListOperations<String, Object> lo = rt.opsForList();
        List range = lo.range(key, 0, -1);
        return range;
    }
}
