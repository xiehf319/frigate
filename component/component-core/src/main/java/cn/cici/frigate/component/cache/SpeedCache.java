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
public class SpeedCache<K, V> {

    // 基于软引用实现的缓存，当内存不够会自动释放缓存内容，以避免OOM
    private ConcurrentHashMap<K, InnerSoftReference<V>> cache;

    // 引用队列，当GC执行后被回收的缓存对象的软引用将被入队，以方便从缓存池中清除失效的软引用
    private ReferenceQueue<V> referenceQueue;

    public SpeedCache() {
        cache = new ConcurrentHashMap<>();
        referenceQueue = new ReferenceQueue<>();
    }

    /**
     * 往缓存添加对象
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        cache.put(key, new InnerSoftReference<>(key, value, referenceQueue));
        // 清除垃圾引用
        clearInvalidReference();
    }

    /**
     * 从缓存中获得值
     * @param key
     * @return
     */
    public V get(K key) {
        synchronized (SpeedCache.class) {
            InnerSoftReference<V> ref = cache.get(key);
            if (ref != null) {
                return ref.get();
            }
        }
        return null;
    }

    /**
     * 判断是否存在key
     * @param key
     * @return
     */
    public boolean contain(K key) {
        return cache.contains(key);
    }

    /**
     * 获取缓存大小
     * @return
     */
    public int size() {
        return cache.size();
    }

    /**
     * 清除失效的软引用
     */
    private void clearInvalidReference() {
        InnerSoftReference<V> innerSoftReference;
        while ((innerSoftReference = (InnerSoftReference<V>) referenceQueue.poll()) != null) {
            cache.remove(innerSoftReference.get());
        }
    }

    /**
     * 清空缓存
     */
    public void clear() {
        cache.clear();
    }


    private class InnerSoftReference<V> extends SoftReference<V> {

        private K key;

        public InnerSoftReference(K key, V value, ReferenceQueue<? super V> referenceQueue) {
            super(value, referenceQueue);
            this.key = key;
        }

        public K getKey() {
            return key;
        }
    }
}
