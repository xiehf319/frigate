package com.frigate.framework.configserver.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author xiehf
 * @date 2018/12/22 22:54
 * @concat 370693739@qq.com
 **/
@Data
@Table(name = "config_group_detail")
@Entity
public class ConfigGroupDetail {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String groupName;

    @Column
    private String keyName;

    @Column
    private String value;
}
