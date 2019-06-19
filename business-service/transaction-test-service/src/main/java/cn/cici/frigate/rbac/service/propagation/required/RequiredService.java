package cn.cici.frigate.rbac.service.propagation.required;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * 在外围方法未开启事务的情况下
 * Propagation.REQUIRED修饰的内部方法会新开启自己的事务，
 * 且开启的事务相互独立，互不干扰。
 *
 *
 * 外围方法开启事务的情况下
 * Propagation.REQUIRED修饰的内部方法会加入到外围方法的事务中，
 * 所有Propagation.REQUIRED修饰的内部方法和外围方法均属于同一事务，
 * 只要一个方法回滚，整个事务均回滚。
 *
 * @createDate:2019/6/14$14:02$
 * @author: Heyfan Xie
 */
@Service
public class RequiredService {

    private static final String TYPE = "required";

    @Autowired
    private RequiredUser1Service user1Service;

    @Autowired
    private RequiredUser2Service user2Service;


    /**
     * 外围方法没有事务
     *      调用有事务的方法1
     *      调用有事务的方法2
     * 外围方法抛出异常
     * ------------------
     * 结果:
     * 方法1执行成功
     * 方法2执行成功
     */
    public void notransactionExceptionRequiredRequried() {
        user1Service.addRequried(TYPE + 1);
        user2Service.addRequried(TYPE + 1);
        throw new RuntimeException();
    }


    /**
     * 外围方法没有事务
     *      调用有事务的方法1
     *      调用有事务的方法2
     *          方法2抛出异常
     * ------------------
     * 方法1执行成功
     * 方法2执行回滚
     */
    public void notransactionequiredRequriedException() {
        user1Service.addRequried(TYPE + 2);
        user2Service.addRequriedException(TYPE + 2);
    }

    /**
     * 外围方法有事务
     *      调用有事务的方法1
     *      调用有事务的方法2
     * 外围方法抛出异常
     * ------------------
     * 方法1执行回滚
     * 方法2执行回滚
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionExceptionRequiredRequired(){
        user1Service.addRequried(TYPE + 3);
        user2Service.addRequried(TYPE + 3);
        throw new RuntimeException();
    }

    /**
     * 外围方法有事务
     *      调用有事务的方法1
     *      调用有事务的方法2
     *          方法2抛出异常
     * ------------------
     * 方法1执行回滚
     * 方法2执行回滚
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionRequiredRequiredException(){
        user1Service.addRequried(TYPE + 4);
        user2Service.addRequriedException(TYPE + 4);
    }


    /**
     * 外围方法有事务
     *      调用有事务的方法1
     * try
     *      调用有事务的方法2
     *          方法2抛出异常
     * catch
     * ------------------
     * 方法1执行回滚
     * 方法2执行回滚
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionRequiredRequiredExceptionTry(){
        user1Service.addRequried(TYPE + 5);
        try {
            user2Service.addRequriedException(TYPE + 5);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }
}
