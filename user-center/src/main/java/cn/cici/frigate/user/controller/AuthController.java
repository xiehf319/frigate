package cn.cici.frigate.user.controller;

import cn.cici.frigate.commons.vo.R;
import cn.cici.frigate.user.properties.SecurityProperties;
import cn.cici.frigate.user.security.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description:
 * @createDate:2019/5/7$13:47$
 * @author: Heyfan Xie
 */
@RestController
public class AuthController {

    @Autowired
    private RedisTokenStore redisTokenStore;

    @Autowired
    private SecurityProperties properties;

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public R<SecurityToken> login(@RequestBody Map<String, String> map) {

        SecurityToken securityToken = new SecurityToken();
        securityToken.setAccessToken("123456");
        securityToken.setExpiresIn(properties.getAccess().getExpires());
        DateTime dateTime = DateTime.now();
        DateTime nextTime = dateTime.plusSeconds(securityToken.getExpiresIn());
        securityToken.setExpiration(nextTime.getMillis());
        securityToken.setRefreshToken("111111");
        securityToken.setRefreshExpiresIn(properties.getRefresh().getExpires());

        SecurityUser securityUser = new SecurityUser();
        securityUser.setUserId("654321");
        securityUser.setUsername("aaaaaa");
        redisTokenStore.storeAccessToken(securityToken, securityUser);
        return R.success(securityToken);
    }


    @RequestMapping(value = "/admin/logout", method = RequestMethod.POST)
    public R  logout() {
        redisTokenStore.removeAccessToken("123456");
        return R.success();
    }
}
