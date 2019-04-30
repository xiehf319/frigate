package cn.cici.frigate.admin.web;

import cn.cici.frigate.admin.clients.ConfigCenterClient;
import cn.cici.frigate.admin.vo.ConfigGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 配置中心管理
 * @createDate:2019/4/25$14:18$
 * @author: Heyfan Xie
 */
@RestController
@Slf4j
public class ConfigController {
    @Autowired
    ConfigCenterClient configCenterClient;

    @PostMapping("/admin/config/center/group")
    public ResponseEntity createConfigGroup(@RequestBody ConfigGroup configGroup) {
        Map<String, String> map = new HashMap<>();
        map.put("name", configGroup.getName());
        map.put("description", configGroup.getDescription());
        return configCenterClient.createGroup(map);
    }
}
