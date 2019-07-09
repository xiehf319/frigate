package cn.cici.frigate.rbac.service.propagation.requirednew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 *
 * 通过这两个方法我们证明了
 * 在外围方法未开启事务的情况下
 * Propagation.REQUIRED修饰的内部方法会新开启自己的事务，
 * 且开启的事务相互独立，互不干扰。
 *
 *
 * 以上试验结果我们证明在外围方法开启事务的情况下
 * Propagation.REQUIRED修饰的内部方法会加入到外围方法的事务中，
 * 所有Propagation.REQUIRED修饰的内部方法和外围方法均属于同一事务，
 * 只要一个方法回滚，整个事务均回滚。
 *
 * https://juejin.im/entry/5a8fe57e5188255de201062b
 *
 * @createDate:2019/6/14$14:02$
 * @author: Heyfan Xie
 */
@Service
public class RequiredNewService {

    private static final String TYPE = "required_new";

    @Autowired
    private RequiredNewUser1Service user1Service;

    @Autowired
    private RequiredNewUser2Service user2Service;



    public void notransactionExceptionRequiredNewRequriedNew() {
        user1Service.addRequriedNew(TYPE + 1);
        user2Service.addRequriedNew(TYPE + 1);
        throw new RuntimeException();
    }


    public void notransactionequiredRequriedException() {
        user1Service.addRequriedNew(TYPE + 2);
        user2Service.addRequriedNewException(TYPE + 2);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionExceptionRequiredRequired(){
        user1Service.addRequriedNew(TYPE + 3);
        user2Service.addRequriedNew(TYPE + 3);
        throw new RuntimeException();
    }

    /**
     * 外围方法开启事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionRequiredRequiredException(){
        user1Service.addRequriedNew(TYPE + 4);
        user2Service.addRequriedNewException(TYPE + 4);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionRequiredRequiredExceptionTry(){
        user1Service.addRequriedNew(TYPE + 5);
        try {
            user2Service.addRequriedNewException(TYPE + 5);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }
}
