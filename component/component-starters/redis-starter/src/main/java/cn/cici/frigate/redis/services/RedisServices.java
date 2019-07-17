package cn.cici.frigate.redis.services;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 14:43
 * @author: Heyfan Xie
 */
public interface RedisServices {

    /**
     * 检查是否存在
     * @param key
     * @return
     */
    default boolean exists(String key) {
        return false;
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    default void delete(String key) {}

    /**
     * 删除key
     * @param key
     * @return
     */
    default long size(String key) {
        return 0;
    }
}
