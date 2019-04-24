package com.mantis.framework.configserver.api;

import com.mantis.framework.configserver.entity.ServiceConfig;
import com.mantis.framework.configserver.jpa.PropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author xiehf
 * @date 2018/12/22 22:56
 * @concat 370693739@qq.com
 **/
@RestController
public class PropertiesApi {

    @Autowired
    private PropertiesRepository repository;

    /**
     * 获取一个服务的所有配置文件
     *
     * @param application
     * @return
     */
    @GetMapping("/api/config/server/properties/{application}")
    private List<ServiceConfig> findByApplication(@PathVariable String application) {
        return repository.findByApplication(application);
    }

    /**
     * 获取一个配置指定环境的配置文件
     *
     * @param application
     * @param profile
     * @return
     */
    @GetMapping("/api/config/server/properties/{application}/{profile}")
    private List<ServiceConfig> findByApplicationAndProfile(@PathVariable String application, @PathVariable String profile) {
        return repository.findByApplicationAndProfile(application, profile);
    }

    /**
     * 更新配置文件, 将现有的直接覆盖历史的
     *
     * @param properties
     */
    @PostMapping("/api/config/server/properties/replace/{application}/{profile}")
    private void replaceProperty(@PathVariable String application,
                                 @PathVariable String profile,
                                 @RequestBody List<ServiceConfig> properties) {

        // 根据application and profile 获取所有配置
        List<ServiceConfig> existsProperties = repository.findByApplicationAndProfile(application, profile);
        properties.forEach(property -> {
            Optional<ServiceConfig> optional = repository.findById(property.getId());

            // 如果存在就更新
            if (optional.isPresent()) {
                repository.updateValue(optional.get().getKey(), optional.get().getValue());
                existsProperties.removeIf(exist -> exist.getKey().equalsIgnoreCase(optional.get().getKey()));
            } else {
                repository.save(property);
            }
        });

        // 被删掉的直接删除
        existsProperties.forEach(property -> {
            repository.delete(property);
        });
    }

}
