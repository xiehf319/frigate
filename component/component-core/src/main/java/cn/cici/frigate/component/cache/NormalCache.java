package cn.cici.frigate.component.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/4/8 17:30
 */
public class NormalCache implements ICache{

    private ConcurrentHashMap<String, String> cache;

    private static class NormalCacheHolder {
        private static NormalCache INSTANCE = new NormalCache();
    }

    public static NormalCache getInstance() {
        return NormalCacheHolder.INSTANCE;
    }

    private NormalCache() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void put(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public String get(String key) {
        return cache.get(key);
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public boolean contain(String key) {
        return cache.contains(key);
    }

    @Override
    public int size() {
        return cache.size();
    }
}
