package cn.cici.frigate.gateway.http;

import cn.cici.frigate.gateway.security.OAuth2AccessToken;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Base64;


/**
 * @author xiehf
 * @date 2019/5/2 22:24
 * @concat 370693739@qq.com
 **/
public class HeaderEnhancerFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeaderEnhancerFilter.class);

    public static final String USER_ID_IN_HEADER = "X-UserId";

    private static final String ANONYMOUS_USER_ID = "363319180390207488";

    public ServerHttpRequest doFilter(ServerHttpRequest request) {
        ServerHttpRequest req = request;

        String authorization = req.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String requestURI = request.getURI().getPath();

        LOGGER.info(String.format("Enhance request URI : %s", requestURI));

        if (!StringUtils.isEmpty(authorization)) {
            if (isJwtBearerToken(authorization)) {
                try {
                    authorization = StringUtils.substringBetween(authorization, ",");
                    String decoded = new String(Base64.getDecoder().decode(authorization));
                    JSONObject object = JSON.parseObject(decoded);
                    String userId = object.getString(USER_ID_IN_HEADER);
                    req = request.mutate().header(USER_ID_IN_HEADER, userId).build();
                } catch (Exception e) {
                    LOGGER.error("Failed to customize header for the request, but still release it as the it would be regarded without any user details.", e);
                }
            }
        } else {
            LOGGER.info("Regard this request as anonymous request, so set anonymous user_id  in the header.");
            req = request.mutate()
                    .header(USER_ID_IN_HEADER, ANONYMOUS_USER_ID)
                    .build();
        }
        return req;
    }


    private boolean isJwtBearerToken(String token) {
        return StringUtils.countMatches(token, ",") == 2
                && token.startsWith(OAuth2AccessToken.BEARER_TYPE);
    }
}
