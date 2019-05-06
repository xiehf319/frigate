package cn.cici.frigate.gateway.filter;

import cn.cici.frigate.commons.utils.JwtUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @createDate:2019/5/6$12:13$
 * @author: Heyfan Xie
 */
@Component
@Slf4j
public class PreFilter extends ZuulFilter {

    /**
     * JWT 添加到头部的前缀
     */
    private static final String JWT_SEPARATOR = "Bearer ";

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("-------->>> TokenFilter {}, {}", request.getMethod(), request.getRequestURI());
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("---------------------------------");
        log.info(authorization);
        log.info("---------------------------------");

        if (StringUtils.isNotBlank(authorization)) {
            String token = authorization.substring(JWT_SEPARATOR.length());
            if (StringUtils.isNotBlank(token)) {
                if (JwtUtil.checkJWT(token)) {
                    ctx.setSendZuulResponse(true);
                    ctx.setResponseStatusCode(HttpStatus.OK.value());
                    return null;
                }
            }
        }
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        ctx.setResponseBody("token must be request");
        return null;
    }
}
