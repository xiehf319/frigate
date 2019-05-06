package cn.cici.frigate.user.security;

import lombok.Data;

import java.util.Set;

/**
 * @author xiehf
 * @date 2019/5/7 0:34
 * @concat 370693739@qq.com
 **/
@Data
public class Request {

    private String clientId;

    private String clientSecret;

    private Set<String> scope;
}
