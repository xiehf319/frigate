package com.frigate.framework.configserver.api;

import com.frigate.framework.configserver.jpa.ConfigGroupDetailRepository;
import com.frigate.framework.configserver.entity.ConfigGroupDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiehf
 * @date 2018/12/22 22:56
 * @concat 370693739@qq.com
 **/
@RestController
public class TemplatesApi {


    @Autowired
    private ConfigGroupDetailRepository repository;


    @GetMapping("/api/config/server/templates/{group}")
    public List<ConfigGroupDetail> findByGroup(String group) {
        return repository.findByGroup(group);
    }


    @GetMapping("/api/config/server/templates")
    public Map<String, List<ConfigGroupDetail>> findAll() {
        return repository.findAll().stream().collect(Collectors.groupingBy(ConfigGroupDetail::getGroupName));
    }

    @PostMapping("/api/config/server/templates/{group}")
    public void addTemplates(@PathVariable String group, List<ConfigGroupDetail> templates) {
        repository.deleteByGroup(group);
        templates.forEach(template -> {
            template.setGroupName(group);
            repository.save(template);
        });
    }

}
