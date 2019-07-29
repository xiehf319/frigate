package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue(generator = "tableIdGenerator")
    @GenericGenerator(name = "tableIdGenerator", strategy = "cn.cici.frigate.rbac.jpa.TableIdGenerator")
    private Long id;

    private Long pid;

    private String menuName;

    private String menuIcon;

    private String menuUrl;

    private String menuType;

    private String menuOrder;

    private String menuStatus;

}
