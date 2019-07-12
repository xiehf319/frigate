package cn.cici.frigate.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/12 9:20
 * @author: Heyfan Xie
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setWithExpire(String key, Object value, long time, TimeUnit timeUnit) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, time, timeUnit);
    }

    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public <K> K get(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return (K) valueOperations.get(key);
    }

    public void addToListLeft(String listKey, long time, TimeUnit timeUnit, Object... values) {
        BoundListOperations<String, Object> boundListOperations = redisTemplate.boundListOps(listKey);
        boundListOperations.leftPushAll(values);
        boundListOperations.expire(time, timeUnit);
    }


    public void addToListRight(String listKey, long time, TimeUnit timeUnit, Object... values) {
        BoundListOperations<String, Object> boundListOperations = redisTemplate.boundListOps(listKey);
        boundListOperations.rightPushAll(values);
        boundListOperations.expire(time, timeUnit);
    }


    public List<Object> rangeList(String listKey, long start, long end) {
        BoundListOperations<String, Object> boundListOperations = redisTemplate.boundListOps(listKey);
        return boundListOperations.range(start, end);
    }
}
