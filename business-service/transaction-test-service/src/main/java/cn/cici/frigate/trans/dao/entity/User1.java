package cn.cici.frigate.trans.dao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @description:
 * @createDate:2019/5/7$10:57$
 * @author: Heyfan Xie
 */
@Data
@Entity
@Table(name = "user1")
public class User1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
