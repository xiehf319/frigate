package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @description: 类介绍：
 *  用户授权表
 * @createDate: 2019/7/18 13:38
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table
public class UserAuth {

    @Id
    @GeneratedValue(generator = "tableIdGenerator")
    @GenericGenerator(name = "tableIdGenerator", strategy = "cn.cici.frigate.rbac.jpa.TableIdGenerator")
    private Long id;

    private Long userId;

    @Column(columnDefinition = "VARCHAR(10) comment '系统用户，邮箱，手机，qq，微信，微博'")
    private String identityType;

    @Column(columnDefinition = "VARCHAR(64) comment '账号,手机号，qq号，微信号，微博号'")
    private String identifier;

    @Column(columnDefinition = "VARCHAR(64) comment '站内账号时密码，第三方时token'")
    private String credential;

    @Column(columnDefinition = "TINYINT(1) comment '是否被验证'")
    private Boolean ifVerified;

}
