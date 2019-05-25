package cn.cici.frigate.gateway.filter;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.gateway.http.HttpConverter;
import cn.cici.frigate.gateway.http.LoginModel;
import cn.cici.frigate.gateway.properties.ClientResourceProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @createDate:2019/5/6$12:13$
 * @author: Heyfan Xie
 */
@Component
@Slf4j
public class LoginFilter extends ZuulFilter {

    private static final String LOGIN_PATH = "/oauth/login";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpConverter httpConverter;

    @Autowired
    private ClientResourceProperties properties;

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
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();
        return requestURI.contains(LOGIN_PATH);
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String clientId = properties.getClient().getClientId();
        String clientSecret = properties.getClient().getClientSecret();
        String auth = "Basic " + Base64Utils.encodeToString((clientId + ":" + clientSecret).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, auth);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        LoginModel userVo = httpConverter.readyBody(request, LoginModel.class);
        params.add("username", userVo.getUsername());
        params.add("password", userVo.getPassword());
        params.add("grant_type", "password");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        try {
            ctx.setSendZuulResponse(false);
            ResponseEntity<Object> responseEntity =
                    restTemplate.exchange("http://auth-service/oauth/login", HttpMethod.POST, requestEntity, Object.class);
            ctx.setResponseStatusCode(responseEntity.getStatusCode().value());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                ctx.setResponseBody(httpConverter.object2Str(R.success(responseEntity.getBody())));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return null;
    }

}
