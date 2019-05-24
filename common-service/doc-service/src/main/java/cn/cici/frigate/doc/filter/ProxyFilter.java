package cn.cici.frigate.doc.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @createDate:2019/5/24$11:52$
 * @author: Heyfan Xie
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "security.oauth2")
@Slf4j
public class ProxyFilter extends ZuulFilter {
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String TOKEN_TYPE = "TOKEN_TYPE";

    private List<String> zuulRoutes = new ArrayList<>();

    private OAuth2RestOperations restTemplate;

    @Autowired
    RouteLocator locator;

    @Autowired
    public void setRestTemplate(OAuth2RestOperations restTemplate) {
        List<Route> list = locator.getRoutes();
        zuulRoutes = list.stream().map(Route::getId).collect(Collectors.toList());
        this.restTemplate = restTemplate;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.containsKey("proxy")) {
            String id = (String) ctx.get("proxy");
            if (!zuulRoutes.contains(id)) {
                return false;
            } else {
                ctx.set(TOKEN_TYPE, "Bearer");
                return true;
            }
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        String accessToken = getAccessToken(ctx);
        ctx.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, ctx.get(TOKEN_TYPE) + " " + accessToken);
        log.info("token {}", accessToken);
        return null;
    }

    private String getAccessToken(RequestContext ctx) {
        String value = (String) ctx.get(ACCESS_TOKEN);
        if (restTemplate != null) {
            try {
                value = restTemplate.getAccessToken().getValue();
            } catch (Exception e) {
                throw new BadCredentialsException("Cannot obtain valid access token");
            }
        }
        return value;
    }


    /**
     * @param zuulRoutes the zuulRoutes to set
     */
    public void setZuulRoutes(List<String> zuulRoutes) {
        this.zuulRoutes = zuulRoutes;
    }
}
