package cn.cici.frigate.gateway.http;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @description:
 * @createDate:2019/5/25$10:06$
 * @author: Heyfan Xie
 */
@Data
@ToString
public class LoginModel implements Serializable {

    private String username;

    private String password;
}
