package cn.cici.frigate.exception;



/**
 * @description:
 * @createDate:2019/7/3$11:57$
 * @author: Heyfan Xie
 */
public interface Assert {

    /**
     * 创建异常
     *
     * @return
     */
    BaseException newException();

    /**
     * 创建异常
     *
     * @param args
     * @return
     */
    BaseException newException(Object... args);

    /**
     * 创建异常
     *
     * @param t
     * @param args
     * @return
     */
    BaseException newException(Throwable t, Object... args);


    /**
     * 断言对象，如果为空抛出异常
     *
     * @param obj
     */
    default void assertNotNull(Object obj) {
        if (obj == null) {
            throw newException();
        }
    }

    /**
     * 断言对象，如果为空抛出异常
     *
     * @param obj
     */
    default void assertNotNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(args);
        }
    }

    /**
     * 断言对象，如果不为空抛出异常
     *
     * @param obj
     */
    default void assertNull(Object obj) {
        if (obj != null) {
            throw newException();
        }
    }

    /**
     * 断言对象，如果不为空抛出异常
     *
     * @param obj
     */
    default void assertNull(Object obj, Object... args) {
        if (obj != null) {
            throw newException(args);
        }
    }

    /**
     * 如果是false抛出异常
     *
     * @param bool
     * @param args
     */
    default void assertTrue(boolean bool, Object... args) {
        if (!bool) {
            throw newException(args);
        }
    }

    /**
     * 如果是true抛出异常
     *
     * @param bool
     * @param args
     */
    default void assertFalse(boolean bool, Object... args) {
        if (bool) {
            throw newException(args);
        }
    }

    /**
     * 如果是空抛出异常
     *
     * @param str
     * @param args
     */
    default void assertNotEmpty(String str, Object... args) {
        if (StringUtils.isEmpty(str)) {
            throw newException(args);
        }
    }

    /**
     * 如果不是空抛出异常
     *
     * @param str
     * @param args
     */
    default void assertEmpty(String str, Object... args) {
        if (StringUtils.isNotEmpty(str)) {
            throw newException(args);
        }
    }
}
