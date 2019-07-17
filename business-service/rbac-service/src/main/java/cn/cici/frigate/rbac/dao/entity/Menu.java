package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 17:54
 * @author: Heyfan Xie
 */
@Entity
@Table(name = "menu")
@Data
public class Menu {

    @Id
    private Long id;

    private Long pid;

    private String menuName;

    private String menuIcon;

    private String menuUrl;

    private String menuType;

    private String menuOrder;

    private String menuStatus;

}
