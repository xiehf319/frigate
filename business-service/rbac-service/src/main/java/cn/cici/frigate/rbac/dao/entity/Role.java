package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 17:32
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(generator = "tableIdGenerator")
    @GenericGenerator(name="tableIdGenerator", strategy = "cn.cici.frigate.rbac.jpa.TableIdGenerator")
    private Long id;

    private String roleName;

    private String roleDesc;

    private String roleTag;

    private List<Permission> permissionList;
}
