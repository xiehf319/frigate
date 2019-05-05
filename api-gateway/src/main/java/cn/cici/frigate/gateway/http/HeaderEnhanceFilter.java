package cn.cici.frigate.gateway.http;

import cn.cici.frigate.gateway.exception.ErrorCode;
import cn.cici.frigate.gateway.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @description:
 * @createDate:2019/5/5$11:21$
 * @author: Heyfan Xie
 */
@Slf4j
public class HeaderEnhanceFilter {

    public static final String X_USER_ID_IN_HEADER = "X-User-Id";

    public static final String BEARER = "Bearer ";

    public ServerHttpRequest doFilter(ServerHttpRequest request) {
        ServerHttpRequest req = request;

        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String requestURI = request.getURI().getPath();
        log.info(String.format("Enhance request URI : %s ", requestURI));

        if (StringUtils.isEmpty(authorization)) {
            throw new ServerException(HttpStatus.UNAUTHORIZED, new ErrorCode(1001, "", ""));
        }
        authorization = authorization.substring(BEARER.length());
        return req;
    }

}
