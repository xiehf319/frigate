package cn.cici.frigate.redis.lock;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

public class RedisLocker<T> implements ApplicationContextAware {

    private static  RedisLockRegistry redisLockRegistry;

    @Override
    public void setApplicationContext(ApplicationContext act) throws BeansException {
        redisLockRegistry = act.getBean(RedisLockRegistry.class);
    }

    public static <T>boolean lock(String key, long time, int loop, Supplier<T> supplier) {
        Lock lock = redisLockRegistry.obtain(key);
        try {
            for (int i = 0; i < loop; i++) {
                boolean b = lock.tryLock(time, TimeUnit.SECONDS);
                if (b) {
                    supplier.get();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            lock.unlock();
        }
    }
}
