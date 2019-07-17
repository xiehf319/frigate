package cn.cici.frigate.redis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 14:39
 * @author: Heyfan Xie
 */
public class RedisStringServices implements RedisServices {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
