package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;

/**
 * @description:
 *  https://juejin.im/entry/5a40594b6fb9a04503104f4e
 * @createDate: 2019/5/9$13:36$
 * @author: Heyfan Xie
 */
@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String mobile;

    private String email;

    private String mark;

    private String rank;

}
