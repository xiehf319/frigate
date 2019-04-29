package cn.cici.frigate.gateway.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @description:
 * @createDate:2019/4/29$16:58$
 * @author: Heyfan Xie
 */
@FeignClient("auth-server")
public interface AuthClient {

    @RequestMapping(value = {"/user"}, produces = "application/json")
    Map<String, Object> user();
}
