package cn.cici.frigate.i18n.manager.dao.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xiehf
 * @description: 类介绍:
 * @date 2019/7/28 15:14
 * @concat 370693739@qq.com
 **/
@Entity
@Table(name="error_code")
@Data
public class ErrorCode {

    @Id
    private Long id;

    private String code;

    private String serviceName;

    private String
}
