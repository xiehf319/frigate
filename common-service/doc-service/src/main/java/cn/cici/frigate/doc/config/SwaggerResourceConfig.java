package cn.cici.frigate.doc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @createDate:2019/5/9$16:09$
 * @author: Heyfan Xie
 */
@Component
@Primary
@Slf4j
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    @Autowired
    RouteLocator routeLocator;

    @Value("${swagger.doc.services}")
    private Set<String> services;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        log.info("route size: {}", routes.size());
        routes.stream().filter(route -> services.contains(route.getId())).distinct().forEach(route -> resources.add(swaggerResource(route.getId(), route.getFullPath().replace(route.getPrefix() + "/**", route.getId() + "/v2/api-docs"))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name: {}, location: {}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("1.0");
        return swaggerResource;
    }


}
