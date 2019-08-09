package cn.cici.frigate.rbac.config;

import cn.cici.frigate.component.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: 类介绍：
 * @createDate: 2019/8/9 17:24
 * @author: Heyfan Xie
 */
@FeignClient("i18n-service")
public interface I18nClient {

    @GetMapping("/i18n/status/code/{serviceName}")
    Map<String, LinkedHashMap<String, String>> findByServiceName(@PathVariable String serviceName);
}
