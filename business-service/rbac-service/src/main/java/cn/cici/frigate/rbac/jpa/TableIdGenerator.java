package cn.cici.frigate.rbac.jpa;

import cn.cici.frigate.component.context.SpringContextUtils;
import cn.cici.frigate.id.client.UidProvider;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author xiehf
 * @description: 类介绍:
 * @date 2019/7/17 21:49
 * @concat 370693739@qq.com
 **/
public class TableIdGenerator implements IdentifierGenerator {

    public TableIdGenerator() {
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        UidProvider uidProvider = SpringContextUtils.getBean(UidProvider.class);
        return uidProvider.getId();
    }
}
