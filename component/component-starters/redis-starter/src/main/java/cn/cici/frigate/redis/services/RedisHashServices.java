package cn.cici.frigate.redis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/16 14:20
 * @author: Heyfan Xie
 */
public class RedisHashServices implements RedisServices {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    /**
     * 获取值
     *
     * @param key
     * @param field
     */
    public String hget(String key, String field) {
        Object val = redisTemplate.opsForHash().get(key, field);
        return val == null ? null : val.toString();
    }


    /**
     * 设置值
     *
     * @param key
     * @return
     */
    public void hset(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 删除hash中field这一对key
     *
     * @param key
     * @param field
     */
    public void hdel(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * 查询所有
     *
     * @param key
     * @return
     */
    public Map<String, String> hgetall(String key) {
        return redisTemplate.execute((RedisCallback<Map<String, String>>) connection -> {
            Map<byte[], byte[]> result = connection.hGetAll(key.getBytes());
            if (CollectionUtils.isEmpty(result)) {
                return new HashMap<>(0);
            }
            Map<String, String> ans = new HashMap<>(result.size());
            for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
                ans.put(new String(entry.getKey()), new String(entry.getValue()));
            }
            return ans;
        });
    }

    /**
     * 批量查询
     *
     * @param key
     * @param fields
     * @return
     */
    public Map<String, String> hmget(String key, List<String> fields) {
        List<Object> result = redisTemplate.opsForHash().multiGet(key, Collections.unmodifiableCollection(fields));
        Map<String, String> ans = new HashMap<>(result.size());
        int index = 0;
        for (String field : fields) {
            if (result.get(index) == null) {
                continue;
            }
            ans.put(field, result.get(index).toString());
            index++;
        }
        return ans;
    }

    /**
     * 自增的可以作为内部单号的生成
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long hincr(String key, String field, long value) {
        return redisTemplate.opsForHash().increment(key, field, value);
    }
}