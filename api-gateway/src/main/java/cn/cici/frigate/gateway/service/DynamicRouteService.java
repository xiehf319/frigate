package cn.cici.frigate.gateway.service;

import cn.cici.frigate.gateway.repository.RedisRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @description:
 * @createDate:2019/4/29$17:46$
 * @author: Heyfan Xie
 */
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RedisRouteDefinitionRepository routeDefinitionWriter;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    private void notifyChanged() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public String add(RouteDefinition definition) {
        this.routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        notifyChanged();
        return "success";
    }

    public String update(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail, not find route routeId: " + definition.getId();
        }
        try {
            return add(definition);
        } catch (Exception e) {
            return "update route fail";
        }
    }

    public String delete(String id) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(id));
            notifyChanged();
            return "delete success";
        }catch (Exception e) {
            return "delete fail";
        }
    }


}
