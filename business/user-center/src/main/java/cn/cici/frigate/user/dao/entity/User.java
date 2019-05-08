package cn.cici.frigate.user.dao.entity;

import cn.cici.frigate.commons.security.SecurityUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @createDate:2019/5/7$10:57$
 * @author: Heyfan Xie
 */
@Data
public class User implements SecurityUser, Serializable {

    private String recordId;

    private String username;

    @Override
    public String getUserId() {
        return recordId;
    }
}
