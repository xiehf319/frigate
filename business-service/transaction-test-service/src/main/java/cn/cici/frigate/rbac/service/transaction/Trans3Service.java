package cn.cici.frigate.rbac.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @createDate:2019/5/9$13:37$
 * @author: Heyfan Xie
 */
@Service
public class Trans3Service {

    @Autowired
    private SaveService saveService;

    public void add() {

        saveService.addUser();
        saveService.addRole();
    }

}
