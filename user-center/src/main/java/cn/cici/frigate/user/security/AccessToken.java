package cn.cici.frigate.user.security;

import lombok.Data;

import java.util.Date;

/**
 * @author xiehf
 * @date 2019/5/7 0:25
 * @concat 370693739@qq.com
 **/
@Data
public class AccessToken {

    private String value;

    private RefreshToken refreshToken;

    private int expiresIn;

    private Date expiration;
}
