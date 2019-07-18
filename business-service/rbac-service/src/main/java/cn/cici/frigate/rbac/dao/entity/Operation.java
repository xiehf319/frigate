package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 17:56
 * @author: Heyfan Xie
 */
@Entity
@Table(name = "operation")
@Data
public class Operation {

    @Id
    private Long id;

    @Column(columnDefinition = "VARCHAR(20) comment '操作名字'")
    private String operationName;

    @Column(columnDefinition = "VARCHAR(50) comment '操作描述'")
    private String operationDesc;

    @Column(columnDefinition = "VARCHAR(50) comment '操作标识'")
    private String operationTag;

}
