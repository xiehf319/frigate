package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @description:
 *  https://juejin.im/entry/5a40594b6fb9a04503104f4e
 * @createDate: 2019/5/9$13:36$
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table(name = "s_user")
public class User {

    @Id
    @GeneratedValue(generator = "tableIdGenerator")
    @GenericGenerator(name="tableIdGenerator", strategy = "cn.cici.frigate.rbac.jpa.TableIdGenerator")
    private Long id;

    private String username;

    private String password;

    private String mobile;

    private String email;

    private String mark;

    private String rank;

    private Date lastLogin;

    private String loginIp;

    private String avatarUrl;

    private Date createTime;

    private Boolean locked;

    private Boolean enabled;

    @ManyToMany(targetEntity = Role.class)
    private List<Role> roleList;
}
