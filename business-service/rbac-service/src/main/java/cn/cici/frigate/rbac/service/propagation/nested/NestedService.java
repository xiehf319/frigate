package cn.cici.frigate.rbac.service.propagation.nested;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 *
 * https://juejin.im/entry/5a8fe57e5188255de201062b
 *
 * @createDate:2019/6/14$14:02$
 * @author: Heyfan Xie
 */
@Service
public class NestedService {

    private static final String TYPE = "NESTED";

    @Autowired
    private NestedUser1Service user1Service;

    @Autowired
    private NestedUser2Service user2Service;


    public void notransactionExceptionNotSupportNotSupport() {
        user1Service.addNested(TYPE + 1);
        user2Service.addNested(TYPE + 1);
        throw new RuntimeException();
    }


    public void notransactionNotSupportNotSupportException() {
        user1Service.addNested(TYPE + 2);
        user2Service.addNestedException(TYPE + 2);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionExceptionNotSupportNotSupport(){
        user1Service.addNested(TYPE + 3);
        user2Service.addNested(TYPE + 3);
        throw new RuntimeException();
    }

    /**
     * 外围方法开启事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionNotSupportNotSupportException(){
        user1Service.addNested(TYPE + 4);
        user2Service.addNestedException(TYPE + 4);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionNotSupportNotSupportExceptionTry(){
        user1Service.addNested(TYPE + 5);
        try {
            user2Service.addNestedException(TYPE + 5);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }
}
