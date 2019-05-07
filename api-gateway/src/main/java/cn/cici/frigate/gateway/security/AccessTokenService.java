package cn.cici.frigate.gateway.security;

import cn.cici.frigate.commons.security.SecurityToken;
import cn.cici.frigate.commons.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiehf
 * @date 2019/5/6 23:03
 * @concat 370693739@qq.com
 **/
@Component
@Slf4j
public class AccessTokenService {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    public SecurityToken login(String username, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        log.info("username {} password {}", username, password);
        R r = restTemplate.postForObject("http://user-center" + "/admin/login", map, R.class);
        log.info("Result {} ", r);
        if (r != null && r.getCode() != HttpStatus.OK.value()) {
            Object data = r.getData();
            log.info("{}", data);
            SecurityToken securityToken = (SecurityToken)data;
            return securityToken;
        }
        return null;
    }
}
