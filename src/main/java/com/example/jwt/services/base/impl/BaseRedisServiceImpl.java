package com.example.jwt.services.base.impl;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.jwt.services.base.BaseRedisService;

@Service
public class BaseRedisServiceImpl implements BaseRedisService {
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;

    public BaseRedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Object hashGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    @Override
    public void hashSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public boolean hashExist(String key, String field) {
        return hashOperations.hasKey(key, field);
    }

}
