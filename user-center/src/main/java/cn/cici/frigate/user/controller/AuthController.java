package cn.cici.frigate.user.controller;

import cn.cici.frigate.commons.security.SecurityToken;
import cn.cici.frigate.commons.security.SecurityUser;
import cn.cici.frigate.commons.vo.R;
import cn.cici.frigate.user.dao.entity.User;
import cn.cici.frigate.user.properties.SecurityProperties;
import cn.cici.frigate.user.security.RedisTokenStore;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 登陆
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public R<SecurityToken> login(@RequestBody Map<String, String> map) {

        SecurityToken securityToken = new SecurityToken();
        securityToken.setAccessToken("123456");
        securityToken.setExpiresIn(properties.getAccess().getExpires());
        DateTime dateTime = DateTime.now();
        DateTime nextTime = dateTime.plusSeconds(securityToken.getExpiresIn());
        securityToken.setExpiration(nextTime.getMillis());
        securityToken.setRefreshToken("111111");
        securityToken.setRefreshExpiresIn(properties.getRefresh().getExpires());

        User securityUser = new User();
        securityUser.setRecordId("aaaaaa");
        securityUser.setUsername("bbbbbbbbb");
        redisTokenStore.storeAccessToken(securityToken, securityUser);
        return R.success(securityToken);
    }


    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public R  logout() {
        redisTokenStore.removeAccessToken("123456");
        return R.success();
    }


    /**
     * 当前登陆用户信息
     *
     * @return
     */
    @RequestMapping(value = "/user/current", method = RequestMethod.GET)
    public R<SecurityUser> currentUser() {
        SecurityUser securityUser = redisTokenStore.readSecurityUserByAccessToken("123456");
        return R.success(securityUser);
    }

    @RequestMapping(value = "/check/token", method = RequestMethod.POST)
    public R checkToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

        String accessToken = authorization.substring("Bearer ".length());
        SecurityUser securityUser = redisTokenStore.readSecurityUserByAccessToken(accessToken);
        if (securityUser == null) {
            return R.error(401, "Invalid token");
        }
        return R.success();
    }

}
