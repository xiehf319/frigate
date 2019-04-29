package cn.cici.frigate.eurekaserver;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @createDate:2019/4/25$11:06$
 * @author: Heyfan Xie
 */
@RestController
@Slf4j
public class ServerController {


    @RequestMapping(value = "/eureka/applications", method = RequestMethod.GET)
    public Object applications() {
        EurekaServerContextHolder instance = EurekaServerContextHolder.getInstance();
        EurekaServerContext serverContext = instance.getServerContext();
        PeerAwareInstanceRegistry registry = serverContext.getRegistry();
        List<Application> applications = registry.getSortedApplications();
        List<Map<String, Object>> apps = new ArrayList<>();
        applications.forEach(application -> {
            String name = application.getName();
            List<InstanceInfo> instances = application.getInstances();
            Map<String, Object> app = new HashMap<>();
            app.put("serviceName", name);

            List<Map<String, Object>> instanceList = new ArrayList<>();
            instances.forEach(instanceInfo -> {
                Map<String, Object> obj = new HashMap<>();
                String ipAddr = instanceInfo.getIPAddr();
                int port = instanceInfo.getPort();
                InstanceInfo.InstanceStatus status = instanceInfo.getStatus();
                String healthCheckUrl = instanceInfo.getHealthCheckUrl();
                obj.put("url", "http://" + ipAddr + ":" + port);
                obj.put("status", status.name());
                obj.put("healthUrl", healthCheckUrl);
                log.info("serviceName: {},  url:  http://{}:{}   status: {}   health: {}", name, ipAddr, port, status.name(), healthCheckUrl);
                instanceList.add(obj);
            });
            app.put("instances", instanceList);
            apps.add(app);
        });
        return apps;
    }


}
