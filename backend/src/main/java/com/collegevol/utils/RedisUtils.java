package com.collegevol.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public final class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    private static RedisTemplate TEMPLATE;

    private RedisUtils(){

    }

    @PostConstruct
    public void init() {
        TEMPLATE = redisTemplate;
    }

    public static RedisTemplate getTemplate(){
        return TEMPLATE;
    }


    public  static void set(String h,String key,Object v){
        TEMPLATE.opsForHash().put(h,key,v);
    }

    public static void delete(String h,String key){
        TEMPLATE.opsForHash().delete(h,key);
    }

    public static Object get(String h, String key){
        return TEMPLATE.opsForHash().get(h,key);
    }

}
