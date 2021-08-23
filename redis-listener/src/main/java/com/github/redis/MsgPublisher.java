package com.github.redis;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Service;


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
