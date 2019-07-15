package cn.cici.frigate.gateway.security;

import cn.cici.frigate.gateway.properties.SecurityConstants;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 13:47
 * @author: Heyfan Xie
 */
@Component
public class HeaderEnhancer {

    public void enhancer(Map<String, Object> principal) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(SecurityConstants.USER_ID_IN_HEADER, String.valueOf(principal.get("id")));
        ctx.addZuulRequestHeader(SecurityConstants.USERNAME_IN_HEADER, String.valueOf(principal.get("username")));
    }
}
