package com.mantis.framework.configserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author xiehf
 * @date 2018/12/22 22:54
 * @concat 370693739@qq.com
 **/
@Data
@Entity(name = "component-list")
public class ComponentList {

    @Id
    private Long id;

    @Column(name = "component", columnDefinition = "VARCHAR(50) not null comment '组名称'")
    private String component;

    @Column(name = "component", columnDefinition = "VARCHAR(200) not null comment 'key'")
    private String key;

    @Column(name = "component", columnDefinition = "VARCHAR(200) not null comment 'value'")
    private String value;
}
