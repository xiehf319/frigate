package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 18:07
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table(name = "role_permission_rel")
public class RolePermission {

    private Long roleId;

    private Long permissionId;
}
