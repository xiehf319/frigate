package cn.cici.frigate.user.security;

import lombok.Data;

/**
 * @author xiehf
 * @date 2019/5/7 0:25
 * @concat 370693739@qq.com
 **/
@Data
public class Authentication {

    private String name;

    private Request request;

    private String username;

    private boolean clientOnly;

    public boolean isClientOnly() {
        return true;
    }
}
