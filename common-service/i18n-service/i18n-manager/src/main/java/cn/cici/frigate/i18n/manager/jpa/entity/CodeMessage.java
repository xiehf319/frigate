package cn.cici.frigate.i18n.manager.jpa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 10:33
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table(name = "code_message")
public class CodeMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long langId;

    @Column(columnDefinition = " varchar(50) not null comment '语言'")
    private String lang;

    @Column(columnDefinition = " varchar(50) not null comment '编码'")
    private String code;

    @Column(columnDefinition = " varchar(200) not null comment '错误信息'")
    private String message;

}
