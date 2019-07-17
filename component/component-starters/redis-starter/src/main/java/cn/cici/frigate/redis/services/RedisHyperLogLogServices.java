package cn.cici.frigate.redis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @description: 类介绍：
 * 适合统计在线人数、访问数量之类的 不需要特别精准的需求
 * @createDate: 2019/7/16 14:20
 * @author: Heyfan Xie
 */
public class RedisHyperLogLogServices implements RedisServices {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加
     *
     * @param key
     * @param objectList
     */
    public void hlset(String key, List<Object> objectList) {
        HyperLogLogOperations<String, Object> logOperations = redisTemplate.opsForHyperLogLog();
        logOperations.add(key, objectList.toArray());
    }

    /**
     * 获取多个key的总数size
     *
     * @param keyList
     * @return
     */
    public long hlcount(List<String> keyList) {
        HyperLogLogOperations<String, Object> logOperations = redisTemplate.opsForHyperLogLog();
        return logOperations.size(keyList.toArray(new String[0]));
    }

    /**
     * 获取一个key的size
     *
     * @param key
     * @return
     */
    public long hlcount(String key) {
        HyperLogLogOperations<String, Object> logOperations = redisTemplate.opsForHyperLogLog();
        return logOperations.size(key);
    }

    /**
     * 删除key
     *
     * @param key
     */
    @Override
    public void delete(String key) {
        HyperLogLogOperations<String, Object> logOperations = redisTemplate.opsForHyperLogLog();
        logOperations.delete(key);
    }

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
}
