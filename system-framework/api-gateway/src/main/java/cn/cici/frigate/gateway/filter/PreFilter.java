package cn.cici.frigate.gateway.filter;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.gateway.properties.PermitAllProperties;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

    /**
     * token用户信息处理器
     */
    @Autowired
    private RedisTokenStore redisTokenStore;

    /**
     * token提取器
     */
    private final TokenExtractor tokenExtractor = new BearerTokenExtractor();

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
