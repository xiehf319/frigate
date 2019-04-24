package com.mantis.framework.configserver.entity;

import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @description: 配置组件 (mysql, redis, 等可以归为一类的配置)
 *
 * @createDate:2019/4/24$11:05$
 * @author: Heyfan Xie
 */
@Data
@Table(appliesTo = "component", comment = "配置分类表")
@Entity(name = "component")
public class Component {

    @Id
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(50) COMMENT default '' '组名称'")
    private String name;

    @Column(name = "name", columnDefinition = "VARCHAR(200) COMMENT default '' '组描述'")
    private String description;

}
