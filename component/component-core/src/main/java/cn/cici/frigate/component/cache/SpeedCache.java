package cn.cici.frigate.component.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiehf
 * @description: 类介绍:
 *  利用软引用 实现高速缓存
 * @date 2019/7/27 20:26
 * @concat 370693739@qq.com
 **/
public class SpeedCache implements ICache{

    // 基于软引用实现的缓存，当内存不够会自动释放缓存内容，以避免OOM
    private ConcurrentHashMap<String, InnerSoftReference> cache;

    // 引用队列，当GC执行后被回收的缓存对象的软引用将被入队，以方便从缓存池中清除失效的软引用
    private ReferenceQueue<String> referenceQueue;

    private static class SpeedCacheHolder {
        private static SpeedCache INSTANCE = new SpeedCache();
    }

    public static SpeedCache getInstance() {
        return SpeedCacheHolder.INSTANCE;
    }

    public SpeedCache() {
        cache = new ConcurrentHashMap<>();
        referenceQueue = new ReferenceQueue<>();
    }

    /**
     * 往缓存添加对象
     * @param key
     * @param value
     */
    @Override
    public void put(String key, String value) {
        cache.put(key, new InnerSoftReference(key, value, referenceQueue));
        // 清除垃圾引用
        clearInvalidReference();
    }

    /**
     * 从缓存中获得值
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        synchronized (SpeedCache.class) {
            InnerSoftReference ref = cache.get(key);
            if (ref != null) {
                return ref.get();
            }
        }
        return null;
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    /**
     * 判断是否存在key
     * @param key
     * @return
     */
    @Override
    public boolean contain(String key) {
        return cache.contains(key);
    }

    /**
     * 获取缓存大小
     * @return
     */
    @Override
    public int size() {
        return cache.size();
    }

    /**
     * 清空缓存
     */
    @Override
    public void clear() {
        cache.clear();
    }


    /**
     * 清除失效的软引用
     */
    private void clearInvalidReference() {
        InnerSoftReference innerSoftReference;
        while ((innerSoftReference = (InnerSoftReference) referenceQueue.poll()) != null) {
            if (innerSoftReference.get() != null) {
                cache.remove(innerSoftReference.get());
            }
        }
    }


    private class InnerSoftReference extends SoftReference<String> {

        private String key;

        public InnerSoftReference(String key, String value, ReferenceQueue<? super String> referenceQueue) {
            super(value, referenceQueue);
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
