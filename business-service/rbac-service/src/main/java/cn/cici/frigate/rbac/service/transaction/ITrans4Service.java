package cn.cici.frigate.rbac.service.transaction;

import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @createDate:2019/5/9$14:33$
 * @author: Heyfan Xie
 */
public interface ITrans4Service {
    void add();

    @Transactional(rollbackFor = Exception.class)
    void addUser();

    @Transactional(rollbackFor = Exception.class)
    void addRole();
}
