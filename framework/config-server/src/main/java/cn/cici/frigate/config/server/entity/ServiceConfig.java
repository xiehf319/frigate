package cn.cici.frigate.config.server.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author xiehf
 * @date 2018/12/22 22:54
 * @concat 370693739@qq.com
 **/
@Data
@Table(name = "service_config")
@Entity
public class ServiceConfig {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String keyName;

    @Column
    private String value;

    @Column
    private String description;

    @Column
    private String application;

    @Column
    private String profile;

    @Column
    private String label;
}
