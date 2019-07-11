package cn.cici.auth.server.security.sms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/11 9:59
 * @author: Heyfan Xie
 */
@RestController
@Slf4j
public class HostController {

    @GetMapping("/host-message")
    public Map<String, Object> getHostMessage() {
        Map<String, Object> map = new HashMap<>(16);
        try {
            InetAddress host = InetAddress.getLocalHost();
            map.put("host", host.getHostName());
            map.put("hostAddress", host.getHostAddress());
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
