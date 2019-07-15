package cn.cici.frigate.trans.service.propagation.notsupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: https://juejin.im/entry/5a8fe57e5188255de201062b
 * @createDate:2019/6/14$14:02$
 * @author: Heyfan Xie
 */
@Service
public class NotSupportService {

    private static final String TYPE = "not_support";

    @Autowired
    private NotSupportUser1Service user1Service;

    @Autowired
    private NotSupportUser2Service user2Service;


    public void notransactionExceptionNotSupportNotSupport() {
        user1Service.addNotSupport(TYPE + 1);
        user2Service.addNotSupport(TYPE + 1);
        throw new RuntimeException();
    }


    public void notransactionNotSupportNotSupportException() {
        user1Service.addNotSupport(TYPE + 2);
        user2Service.addNotSupportException(TYPE + 2);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionExceptionNotSupportNotSupport() {
        user1Service.addNotSupport(TYPE + 3);
        user2Service.addNotSupport(TYPE + 3);
        throw new RuntimeException();
    }

    /**
     * 外围方法开启事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionNotSupportNotSupportException() {
        user1Service.addNotSupport(TYPE + 4);
        user2Service.addNotSupportException(TYPE + 4);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionNotSupportNotSupportExceptionTry() {
        user1Service.addNotSupport(TYPE + 5);
        try {
            user2Service.addNotSupportException(TYPE + 5);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }
}
