package cn.cici.frigate.logistic.service.propagation.required;

import cn.cici.frigate.logistics.LogisticsApplication;
import cn.cici.frigate.logistics.service.propagation.nested.NestedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @createDate:2019/6/14$16:22$
 * @author: Heyfan Xie
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogisticsApplication.class)
public class NestedServiceTest {

    @Autowired
    private NestedService nestedService;


    /**
     * 外围方法没有事务
     * 调用有事务的方法1
     * 调用有事务的方法2
     * 外围方法抛出异常
     * ------------------
     * 结果:
     * 方法1执行成功
     * 方法2执行成功
     */
    @Test
    public void notransactionExceptionRequiredRequried() {
        nestedService.notransactionExceptionNotSupportNotSupport();
    }

    /**
     * 外围方法没有事务
     * 调用有事务的方法1
     * 调用有事务的方法2
     * 方法2抛出异常
     * ------------------
     * 方法1执行成功
     * 方法2执行回滚
     */
    @Test
    public void notransactionequiredRequriedException() {
        nestedService.notransactionNotSupportNotSupportException();
    }


    /**
     * 外围方法有事务
     * 调用有事务的方法1
     * 调用有事务的方法2
     * 外围方法抛出异常
     * ------------------
     * 方法1执行回滚
     * 方法2执行回滚
     */
    @Test
    public void transactionExceptionRequiredRequired() {
        nestedService.transactionExceptionNotSupportNotSupport();
    }


    /**
     * 外围方法有事务
     * 调用有事务的方法1
     * 调用有事务的方法2
     * 方法2抛出异常
     * ------------------
     * 方法1执行回滚
     * 方法2执行回滚
     */
    @Test
    public void transactionRequiredRequiredException() {
        nestedService.transactionNotSupportNotSupportException();
    }

    /**
     * 外围方法有事务
     * 调用有事务的方法1
     * try
     * 调用有事务的方法2
     * 方法2抛出异常
     * catch
     * ------------------
     * 方法1执行回滚
     * 方法2执行回滚
     */
    @Test
    public void transactionRequiredRequiredExceptionTry() {
        nestedService.transactionNotSupportNotSupportExceptionTry();
    }
}