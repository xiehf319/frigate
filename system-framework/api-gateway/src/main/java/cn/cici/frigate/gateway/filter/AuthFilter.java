package cn.cici.frigate.gateway.filter;

import cn.cici.frigate.gateway.properties.ClientResourceProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @description:
 * @createDate:2019/5/6$12:13$
 * @author: Heyfan Xie
 */
@Component
@Slf4j
public class AuthFilter extends ZuulFilter {

    @Autowired
    private ClientResourceProperties properties;

    @Autowired
    private PathMatcher pathMatcher;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();

        Set<String> ignorePatterns = properties.getClient().getIgnorePatterns();
        for (String ignorePattern : ignorePatterns) {
            if (pathMatcher.match(ignorePattern, requestURI)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("请求地址： {}", request.getRequestURI());
        return null;
    }

}
