package com.frigate.framework.configserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 配置组件 (mysql, redis, 等可以归为一类的配置)
 * @createDate:2019/4/24$11:05$
 * @author: Heyfan Xie
 */
@Data
@Table(name = "config_group")
public class ConfigGroup {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

}
