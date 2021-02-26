package com.github.redis;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Service;

/**
 * @Title: frigate
 * @Package com.github.redis
 * @Description: (用一句话描述该文件做什么)
 * @Author: 003300
 * @Date: 2021/2/26
 * @Version V1.0
 * @Copyright: 2020 Shenzhen Hive Box Technology Co.,Ltd All rights reserved.
 * @Note: This content is limited to the internal circulation of Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
@Service
public class MsgPublisher {

    @Autowired
    private RedisConnection redisConnection;

    public void publish(String channel, Object message) {
        if (message instanceof String) {
            redisConnection.publish(channel.getBytes(), String.valueOf(message).getBytes());
        } else {
            Gson gson = new Gson();
            redisConnection.publish(channel.getBytes(), gson.toJson(message).getBytes());
        }
    }
}
