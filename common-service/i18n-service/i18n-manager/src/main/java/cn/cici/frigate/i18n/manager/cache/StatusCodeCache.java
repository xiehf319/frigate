package cn.cici.frigate.i18n.manager.cache;

import cn.cici.frigate.i18n.manager.jpa.entity.CodeMessage;
import cn.cici.frigate.redis.services.RedisHashServices;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 11:04
 * @author: Heyfan Xie
 */
@Component
public class StatusCodeCache {

    @Autowired
    private RedisHashServices redisHashServices;


    private static final String I18N_PREFIX = "I18N:";

    private String getKey(String key){
        return I18N_PREFIX + key;
    }

    /**
     * 添加到缓存
     *
     * @param serviceName   服务名
     * @param lang  编码
     * @param messages  消息
     */
    public void add(String serviceName, String lang, List<CodeMessage> messages) {
        redisHashServices.hset(getKey(serviceName), lang, JSONObject.toJSONString(messages));
    }

    /**
     * 根据服务名获取所有的
     *
     * @param serviceName
     * @return
     */
    public Map<String, List<CodeMessage>> getAll(String serviceName) {
        Map<String, String> all = redisHashServices.hgetall(getKey(serviceName));
        if (all == null) {
            return Maps.newHashMap();
        }
        Map<String, List<CodeMessage>> map = Maps.newHashMap();
        all.forEach((key, value) -> {
            map.put(key, JSONObject.parseArray(value, CodeMessage.class));
        });
        return map;
    }


}
