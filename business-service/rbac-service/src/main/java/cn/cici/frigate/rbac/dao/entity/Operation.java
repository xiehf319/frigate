package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @GeneratedValue(generator = "tableIdGenerator")
    @GenericGenerator(name="tableIdGenerator", strategy = "cn.cici.frigate.rbac.jpa.TableIdGenerator")
    private Long id;

    private String operationDesc;

    private String operationName;

    private String operationTag;

}
