package cn.cici.frigate.id.api;

import cn.cici.frigate.id.UidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @createDate:2019/7/9$10:55$
 * @author: Heyfan Xie
 */
@RestController
public class UIdApi implements UIdRemote {

    @Autowired
    private UidGenerator uidGenerator;

    @Override
    public long getId() {
        return uidGenerator.getUID();
    }
}
