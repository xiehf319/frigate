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
    private Long id;

    private String serviceId;

    private String method;

    private String url;
}
