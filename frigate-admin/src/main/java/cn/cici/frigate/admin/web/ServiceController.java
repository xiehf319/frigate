package cn.cici.frigate.admin.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @description:
 * @createDate:2019/4/25$9:31$
 * @author: Heyfan Xie
 */
@RestController
public class ServiceController {

    @Value("${eureka.client.service-url.defaultZone: http://localhost:8761/eureka}")
    private String eurekaUrl;

    @RequestMapping("/admin/server")
    public Object server() {
        String url = eurekaUrl.split(",")[0];
        URI uri = UriComponentsBuilder.fromHttpUrl(url + "/applications")
                .build().encode().toUri();
        RestTemplate restTemplate = new RestTemplate();
        Object response = restTemplate.getForObject(uri, Object.class);
        return response;
    }


}
