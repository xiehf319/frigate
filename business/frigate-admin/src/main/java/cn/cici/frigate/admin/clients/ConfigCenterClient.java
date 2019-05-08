package cn.cici.frigate.admin.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * @description:
 * @createDate:2019/4/25$14:21$
 * @author: Heyfan Xie
 */
@FeignClient("config-server")
public interface ConfigCenterClient {

    @RequestMapping(value = "/config/group", method = RequestMethod.POST)
    ResponseEntity createGroup(@RequestBody Map<String, String> map);

}
