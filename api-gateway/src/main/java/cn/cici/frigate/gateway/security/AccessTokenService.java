package cn.cici.frigate.gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author xiehf
 * @date 2019/5/6 23:03
 * @concat 370693739@qq.com
 **/
@Component
@Slf4j
public class AccessTokenService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public void checkToken(String token) {
        ServiceInstance instance = loadBalancerClient.choose("user-server");
        if (instance == null) {

        } else {
            String uri = instance.getUri().toString();
            log.info(uri);
        }
    }
}
