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
@Table(name = "permission")
public class Permission {

    private Long id;

    private String permissionDesc;

    private String permissionName;

    private Long menuId;

}
