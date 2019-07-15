package cn.cici.frigate.config.server.api;

import cn.cici.frigate.config.server.entity.ConfigGroup;
import cn.cici.frigate.config.server.jpa.ConfigGroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @description:
 * @createDate:2019/4/25$14:22$
 * @author: Heyfan Xie
 */
@RestController
@Slf4j
public class ConfigGroupApi {

    @Autowired
    private ConfigGroupRepository repository;

    @PostMapping("/config/group")
    public ResponseEntity createGroup(@RequestBody Map<String, String> map) {
        ConfigGroup group = new ConfigGroup();
        group.setName(map.get("name"));
        group.setDescription(map.get("description"));
        repository.save(group);
        return ResponseEntity.ok().build();
    }
}
