package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 17:32
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table(name = "role")
public class Role {

    private Long id;

    private String roleName;

    private String roleDesc;

    private String roleTag;

}
