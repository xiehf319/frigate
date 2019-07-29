package cn.cici.frigate.rbac.dao.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/18 10:27
 * @author: Heyfan Xie
 */
@Data
public class Resources {

    @Id
    @GeneratedValue(generator = "tableIdGenerator")
    @GenericGenerator(name = "tableIdGenerator", strategy = "cn.cici.frigate.rbac.jpa.TableIdGenerator")
    private Long id;

    private String serviceId;

    private String method;

    private String url;
}
