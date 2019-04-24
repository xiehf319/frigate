package com.mantis.framework.configserver.api;

import com.mantis.framework.configserver.entity.ComponentList;
import com.mantis.framework.configserver.jpa.TemplatesRepository;
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
    private TemplatesRepository repository;


    @GetMapping("/api/config/server/templates/{group}")
    public List<ComponentList> findByGroup(String group) {
        return repository.findByGroup(group);
    }


    @GetMapping("/api/config/server/templates")
    public Map<String, List<ComponentList>> findAll() {
        return repository.findAll().stream().collect(Collectors.groupingBy(ComponentList::getComponent));
    }

    @PostMapping("/api/config/server/templates/{group}")
    public void addTemplates(@PathVariable String group, List<ComponentList> templates) {
        repository.deleteByGroup(group);
        templates.forEach(template -> {
            template.setComponent(group);
            repository.save(template);
        });
    }

    public static void main(String[] args) {

        System.out.println("jdbc:mysql://localhost:3306/config-server?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC".length());
    }
}
