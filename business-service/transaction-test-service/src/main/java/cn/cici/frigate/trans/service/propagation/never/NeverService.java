package cn.cici.frigate.trans.service.propagation.never;

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
public class NeverService {

    private static final String TYPE = "NEVER";

    @Autowired
    private NeverUser1Service user1Service;

    @Autowired
    private NeverUser2Service user2Service;


    public void notransactionExceptionNotSupportNotSupport() {
        user1Service.addNever(TYPE + 1);
        user2Service.addNever(TYPE + 1);
        throw new RuntimeException();
    }


    public void notransactionNotSupportNotSupportException() {
        user1Service.addNever(TYPE + 2);
        user2Service.addNeverException(TYPE + 2);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionExceptionNotSupportNotSupport(){
        user1Service.addNever(TYPE + 3);
        user2Service.addNever(TYPE + 3);
        throw new RuntimeException();
    }

    /**
     * 外围方法开启事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionNotSupportNotSupportException(){
        user1Service.addNever(TYPE + 4);
        user2Service.addNeverException(TYPE + 4);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionNotSupportNotSupportExceptionTry(){
        user1Service.addNever(TYPE + 5);
        try {
            user2Service.addNeverException(TYPE + 5);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }
}
