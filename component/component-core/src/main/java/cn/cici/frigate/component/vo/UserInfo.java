package cn.cici.frigate.component.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/12 18:12
 * @author: Heyfan Xie
 */
@Data
public class UserInfo {

    private long id;

    private String username;

    private String password;

    private List<RoleInfo> roleList;
}
