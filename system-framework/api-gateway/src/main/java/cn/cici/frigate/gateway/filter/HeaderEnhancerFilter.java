package cn.cici.frigate.gateway.filter;

import cn.cici.frigate.gateway.properties.PermitAllProperties;
import cn.cici.frigate.gateway.properties.SecurityConstants;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/12 16:45
 * @author: Heyfan Xie
 */
@Slf4j
public class HeaderEnhancerFilter implements Filter {

    public static final String ANONYMOUS_USER_ID = "XXXXXXXXXXXXXXXXXXXXXXXXXXX";

    @Autowired
    private PermitAllProperties permitAllProperties;

    @Autowired
    private UserInfoTokenServices userInfoTokenServices;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String authorization = servletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String requestURI = servletRequest.getRequestURI();

        log.info(MessageFormat.format("Enhancer request URI: {0}", requestURI));

        if (isPermitAll(requestURI) && isNotOAuthEndpoint(requestURI)) {
            HttpServletRequest resetRequest = removeValueFromRequestHeader(servletRequest);
            chain.doFilter(resetRequest, response);
            return;
        }

        if (!StringUtils.isEmpty(authorization)) {
            if (isBearToken(authorization)) {
                try {
                    // todo token 检验token
                    OAuth2Authentication authentication = userInfoTokenServices.loadAuthentication(authorization.substring("Bearer ".length()));
                    Object principal = authentication.getPrincipal();
                    log.info("用户信息: " + principal.toString());
                    authorization = StringUtils.substringBetween(authorization, ".");
                    String decoded = new String(Base64.decodeBase64(authorization));

                    JSONObject jsonObject = JSONObject.parseObject(decoded);
                    String userId = jsonObject.getString(SecurityConstants.USER_ID_IN_HEADER);
                    RequestContext.getCurrentContext().addZuulRequestHeader(SecurityConstants.USER_ID_IN_HEADER, userId);
                } catch (Exception e) {
                    log.error("Failed to customize header for the request, but still release it as the it would be regarded without any user details.", e);
                }
            }
        } else {
            log.info("Regard this request as anonymous request, so set anonymous user_id in the header.");
            RequestContext.getCurrentContext().addZuulRequestHeader(SecurityConstants.USER_ID_IN_HEADER, ANONYMOUS_USER_ID);
        }
        chain.doFilter(request, response);
    }

    private boolean isBearToken(String authorization) {
        return authorization.startsWith("Bearer");
    }

    private boolean isJwtBearToken(String authorization) {
        return StringUtils.countMatches(authorization, ".") == 2
                && (authorization.startsWith("Bearer") || authorization.startsWith("bearer"));
    }

    @Override
    public void destroy() {

    }


    private HttpServletRequestWrapper removeValueFromRequestHeader(HttpServletRequest request) {

        HttpServletRequestWrapper httpServletRequestWrapper = new HttpServletRequestWrapper(request) {

            private Set<String> headerNameSet;

            private Set<String> headerSet;

            @Override
            public Enumeration<String> getHeaderNames() {
                if (headerNameSet == null) {
                    headerNameSet = new HashSet<>();

                    Enumeration<String> headerNames = super.getHeaderNames();
                    while (headerNames.hasMoreElements()) {
                        String headerName = headerNames.nextElement();
//                        if (!HttpHeaders.AUTHORIZATION.equalsIgnoreCase(headerName)) {
                        headerNameSet.add(headerName);
//                        }
                    }
                    headerNameSet.add(SecurityConstants.USER_ID_IN_HEADER);
                }
                return Collections.enumeration(headerNameSet);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
//                if (HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name)) {
//                    return Collections.emptyEnumeration();
//                }
                if (SecurityConstants.USER_ID_IN_HEADER.equalsIgnoreCase(name)) {
                    headerSet = new HashSet<>();
                    headerSet.add(ANONYMOUS_USER_ID);
                    return Collections.enumeration(headerSet);
                }
                return super.getHeaders(name);
            }

            @Override
            public String getHeader(String name) {
                if (HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name)) {
                    return null;
                }
                if (SecurityConstants.USER_ID_IN_HEADER.equalsIgnoreCase(name)) {
                    return ANONYMOUS_USER_ID;
                }
                return super.getHeader(name);
            }
        };

        return httpServletRequestWrapper;
    }

    private boolean isNotOAuthEndpoint(String requestUri) {
        return !requestUri.contains("/oauth");
    }


    private boolean isPermitAll(String url) {
        return permitAllProperties.isPermitAllUrl(url);
    }
}
