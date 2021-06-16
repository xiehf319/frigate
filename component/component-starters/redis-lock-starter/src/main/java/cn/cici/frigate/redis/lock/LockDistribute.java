package cn.cici.frigate.redis.lock;


import java.lang.annotation.*;

/**
 * @author 003300
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockDistribute {

    String value() default "";

    int time() default 30;
}