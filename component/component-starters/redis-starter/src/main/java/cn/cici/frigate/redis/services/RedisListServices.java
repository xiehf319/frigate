package cn.cici.frigate.redis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 14:39
 * @author: Heyfan Xie
 */
public class RedisListServices implements RedisServices {

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

    /**
     * 头部批量存储值
     *
     * @param key    key
     * @param values 值集合
     */
    public void leftPushAll(String key, String... values) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.leftPushAll(key, values);
    }

    /**
     * 头部单个存储值
     *
     * @param key   key
     * @param value 值
     */
    public void leftPush(String key, String value) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(key, value);
    }

    /**
     * 头部获取值
     *
     * @param key key
     */
    public String leftPop(String key) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.leftPop(key);
    }

    /**
     * 尾部批量存储值
     *
     * @param key    key
     * @param values 值集合
     */
    public void rightPushAll(String key, String values) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPushAll(key, values);
    }

    /**
     * 尾部单个存储值
     *
     * @param key   key
     * @param value 值
     */
    public void rightPush(String key, String value) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        listOperations.rightPush(key, value);
    }

    /**
     * 尾部获取值
     *
     * @param key key
     */
    public String rightPop(String key) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.rightPop(key);
    }

    /**
     * 获取子集合
     *
     * @param key   key
     * @param start 开始下标
     * @param end   结束下标
     * @return
     */
    public List<String> range(String key, int start, int end) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.range(key, start, end);
    }

    @Override
    public long size(String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> connection.lLen(key.getBytes()));
    }
}
