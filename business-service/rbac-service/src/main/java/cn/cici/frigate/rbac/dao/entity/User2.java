package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @description:
 * @createDate:2019/5/7$10:57$
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table(name = "user2")
public class User2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
