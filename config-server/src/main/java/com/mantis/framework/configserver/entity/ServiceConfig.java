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
@Entity(name = "service-config")
public class ServiceConfig {

    @Id
    private Long id;

    @Column
    private String key;

    @Column
    private String value;

    @Column
    private String description;

    @Column
    private String profile;

    @Column
    private String label;
}
