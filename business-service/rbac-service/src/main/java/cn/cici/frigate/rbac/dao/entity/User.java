package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @description: https://juejin.im/entry/5a40594b6fb9a04503104f4e
 * @createDate: 2019/5/9$13:36$
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table(name = "s_user")
public class User {

    @Id
    @GeneratedValue(generator = "tableIdGenerator")
    @GenericGenerator(name = "tableIdGenerator", strategy = "cn.cici.frigate.rbac.jpa.TableIdGenerator")
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(50) comment '用户名'")
    private String username;

    @Column(columnDefinition = "VARCHAR(64) comment '手机号码'")
    private String mobile;

    @Column(columnDefinition = "VARCHAR(64) comment '邮箱'")
    private String email;

    @Column(columnDefinition = "VARCHAR(500) comment '备注'")
    private String remark;

    @Column(columnDefinition = "VARCHAR(254) comment '头像url'")
    private String nickName;

    @Column(columnDefinition = "VARCHAR(254) comment '头像url'")
    private String avatarUrl;

    @Column(columnDefinition = "TINYINT(1) comment '是否锁定'")
    private Boolean locked;

    @Column(columnDefinition = "TINYINT(1) comment '是否禁用'")
    private Boolean enabled;

    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "user_role_rel",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @OneToMany
    @JoinColumn(name="userId")
    private Set<UserAuth> userAuths;
}
