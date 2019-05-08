package cn.cici.frigate.config.server.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @description: 配置组件 (mysql, redis, 等可以归为一类的配置)
 * @createDate:2019/4/24$11:05$
 * @author: Heyfan Xie
 */
@Data
@Table(name = "config_group")
@Entity
public class ConfigGroup {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

}
