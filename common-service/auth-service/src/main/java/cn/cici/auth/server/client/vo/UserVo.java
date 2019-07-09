package cn.cici.auth.server.client.vo;

import lombok.Data;

/**
 * @description:
 * @createDate:2019/7/9$16:01$
 * @author: Heyfan Xie
 */
@Data
public class UserVo {

    private long id;

    private String username;

    private String password;

    private String salt;

    private boolean enable;
}
