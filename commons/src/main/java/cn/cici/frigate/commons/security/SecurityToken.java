package cn.cici.frigate.commons.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @createDate:2019/5/7$14:10$
 * @author: Heyfan Xie
 */
@Data
public class SecurityToken implements Serializable {

    private String accessToken;

    private String refreshToken;

    private Integer expiresIn;

    private Long expiration;

    @JsonIgnore
    private Integer refreshExpiresIn;
}
