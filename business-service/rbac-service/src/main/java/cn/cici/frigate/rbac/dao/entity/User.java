package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;

/**
 * @description:
 * @createDate:2019/5/9$13:36$
 * @author: Heyfan Xie
 */
@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String salt;

}
