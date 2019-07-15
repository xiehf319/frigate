package cn.cici.frigate.gateway.filter;

import cn.cici.frigate.gateway.properties.PermitAllProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 9:58
 * @author: Heyfan Xie
 */
@Component
@Slf4j
public class PreFilter extends ZuulFilter {

    @Autowired
    private PermitAllProperties permitAllProperties;

    @Autowired
    private PathMatcher pathMatcher;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String requestURI = ctx.getRequest().getRequestURI();
        for (String permitAllPattern : permitAllProperties.getPermitAllPatterns()) {
            if (pathMatcher.match(permitAllPattern, requestURI)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        return null;
    }
}
