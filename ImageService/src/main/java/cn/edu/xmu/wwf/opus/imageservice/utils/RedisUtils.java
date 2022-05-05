package cn.edu.xmu.wwf.opus.imageservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils<K,V> {
    @Autowired
    RedisTemplate<K,V> redisTemplate;
    public void add(K key,V value){
        redisTemplate.opsForValue().set(key, value);
    }
    public V get(K key){
        return (V)redisTemplate.opsForValue().get(key);
    }
    public boolean hasKey(K key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
