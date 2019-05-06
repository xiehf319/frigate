package cn.cici.frigate.gateway.security;

import lombok.Data;

/**
 * @author xiehf
 * @date 2019/5/6 23:05
 * @concat 370693739@qq.com
 **/
@Data
public class JwtAccessToken {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiresIn;

    private String scope;

    private String jti;
}
