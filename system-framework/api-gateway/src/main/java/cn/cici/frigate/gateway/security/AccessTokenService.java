package cn.cici.frigate.gateway.security;

import cn.cici.frigate.component.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    public R checkToken(String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorization);
        HttpEntity httpEntity = new HttpEntity(headers);
        R r = restTemplate.postForObject("http://rbac-center/check/token", httpEntity, R.class);
        return r;
    }
}
