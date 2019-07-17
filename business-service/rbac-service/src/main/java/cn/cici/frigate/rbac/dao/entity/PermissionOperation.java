package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 18:04
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table(name = "permission_operation_rel")
public class PermissionOperation {

    private Long permissionId;

    private Long operationId;
}
