package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/18 13:45
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table
public class UserLog {

    @Id
    @GeneratedValue(generator = "tableIdGenerator")
    @GenericGenerator(name = "tableIdGenerator", strategy = "cn.cici.frigate.rbac.jpa.TableIdGenerator")
    private Long id;

    private Long userAuthId;

    private Date lastLoginTime;

    private String loginIp;

}
