package cn.cici.frigate.msg.pojo;

import lombok.Data;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/12 10:15
 * @author: Heyfan Xie
 */
@Data
public class User {

    private long id;

    private String username;

    private String password;

    private String mobile;

    private String email;

}
