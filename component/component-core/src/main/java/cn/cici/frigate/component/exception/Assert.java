package cn.cici.frigate.component.exception;

/**
 * @description:
 * @createDate:2019/7/3$11:57$
 * @author: Heyfan Xie
 */
public interface Assert {

    /**
     * 创建异常
     * @return
     */
    BaseException newException();

    /**
     * 创建异常
     * @param args
     * @return
     */
    BaseException newException(Object... args);

    /**
     * 创建异常
     * @param t
     * @param args
     * @return
     */
    BaseException newException(Throwable t, Object... args);


    /**
     * 断言对象，如果为空抛出异常
     * @param obj
     */
    default void assertNotNull(Object obj) {
        if(obj == null) {
            throw newException();
        }
    }

    /**
     * 断言对象，如果为空抛出异常
     * @param obj
     */
    default void assertNotNull(Object obj, Object... args) {
        if(obj == null) {
            throw newException(args);
        }
    }
}
