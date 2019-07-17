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

    private String permissionDesc;

    private String permissionName;

    @OneToOne
    private Menu menu;

    private List<Operation> operationList;

}
