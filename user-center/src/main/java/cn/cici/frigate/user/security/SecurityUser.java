package cn.cici.frigate.user.security;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiehf
 * @date 2019/5/7 0:25
 * @concat 370693739@qq.com
 **/
@Data
public class SecurityUser implements Serializable {

    private String userId;

    private String username;

}
