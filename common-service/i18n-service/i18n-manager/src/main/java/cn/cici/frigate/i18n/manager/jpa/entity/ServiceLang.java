package cn.cici.frigate.i18n.manager.jpa.entity;


import lombok.Data;

import javax.persistence.*;

/**
 * @author xiehf
 * @description: 类介绍:
 * 状态码
 * @date 2019/7/28 15:14
 * @concat 370693739@qq.com
 **/
@Entity
@Table(name = "service_lang")
@Data
public class ServiceLang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = " varchar(50) not null comment '语言'")
    private String lang;

    @Column(columnDefinition = " varchar(50) not null comment '语言中文描述'")
    private String langCN;

    @Column(columnDefinition = " varchar(50) not null comment '服务名'")
    private String serviceName;

}
