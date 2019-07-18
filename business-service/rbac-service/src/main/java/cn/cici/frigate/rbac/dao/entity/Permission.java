package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 17:32
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(generator = "tableIdGenerator")
    @GenericGenerator(name="tableIdGenerator", strategy = "cn.cici.frigate.rbac.jpa.TableIdGenerator")
    private Long id;

    @Column(columnDefinition = "TINYINT(1) comment '权限类型(1 菜单 2操作 3资源)'")
    private Integer permissionType;

}
