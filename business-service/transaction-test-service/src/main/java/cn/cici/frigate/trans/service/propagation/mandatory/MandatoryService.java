package cn.cici.frigate.trans.service.propagation.mandatory;

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
public class MandatoryService {

    private static final String TYPE = "Mandatory";

    @Autowired
    private MandatoryUser1Service user1Service;

    @Autowired
    private MandatoryUser2Service user2Service;


    public void notransactionExceptionNotSupportNotSupport() {
        user1Service.addMandatory(TYPE + 1);
        user2Service.addMandatory(TYPE + 1);
        throw new RuntimeException();
    }


    public void notransactionNotSupportNotSupportException() {
        user1Service.addMandatory(TYPE + 2);
        user2Service.addMandatoryException(TYPE + 2);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionExceptionNotSupportNotSupport() {
        user1Service.addMandatory(TYPE + 3);
        user2Service.addMandatory(TYPE + 3);
        throw new RuntimeException();
    }

    /**
     * 外围方法开启事务
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionNotSupportNotSupportException() {
        user1Service.addMandatory(TYPE + 4);
        user2Service.addMandatoryException(TYPE + 4);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionNotSupportNotSupportExceptionTry() {
        user1Service.addMandatory(TYPE + 5);
        try {
            user2Service.addMandatoryException(TYPE + 5);
        } catch (Exception e) {
            System.out.println("方法回滚");
        }
    }
}
