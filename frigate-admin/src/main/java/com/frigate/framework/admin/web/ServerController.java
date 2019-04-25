package com.frigate.framework.admin.web;

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
public class ServerController {

    @RequestMapping("/server")
    public Object server() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8761/applications")
                .build().encode().toUri();
        RestTemplate restTemplate = new RestTemplate();
        Object response = restTemplate.getForObject(uri, Object.class);
        return response;
    }

}
