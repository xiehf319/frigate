package cn.cici.frigate.redis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description: 类介绍：
 * 用于数据量上亿的场景下，例如几亿用户系统的签到，去重登陆次数统计，某用书是否在线状态等等
 * @createDate: 2019/7/16 14:20
 * @author: Heyfan Xie
 */
public class RedisBitServices implements RedisServices {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 添加值
     *
     * @param key
     * @param field
     * @param value
     */
    public void setBit(String key, long field, boolean value) {
        redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.setBit(key.getBytes(), field, value));
    }

    /**
     * 统计值
     *
     * @param key
     * @return
     */
    public long bitCount(final String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes()));
    }

    /**
     * 检查value是否已经存在
     *
     * @param key
     * @param field
     * @return
     */
    public Boolean getBit(String key, long field) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.getBit(key.getBytes(), field));
    }


}
