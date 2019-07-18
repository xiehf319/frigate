package cn.cici.frigate.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 9:56
 * @author: Heyfan Xie
 */
@Component
@Slf4j
public class PostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        String contentType = ctx.getResponse().getContentType();
        log.info("contentType: {}", contentType);
        if (StringUtils.isEmpty(contentType)) {
            ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        }
        String responseBody = ctx.getResponseBody();
        log.info(responseBody);
        return null;
    }
}
