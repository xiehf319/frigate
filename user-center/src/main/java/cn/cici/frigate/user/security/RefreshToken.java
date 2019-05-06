package cn.cici.frigate.user.security;

import lombok.Data;

import java.util.Date;

/**
 * @author xiehf
 * @date 2019/5/7 0:23
 * @concat 370693739@qq.com
 **/
@Data
public class RefreshToken {

    private String value;

    private Date expiration;
}
