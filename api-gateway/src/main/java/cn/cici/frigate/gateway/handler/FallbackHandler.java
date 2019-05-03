package cn.cici.frigate.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author xiehf
 * @date 2019/5/2 22:49
 * @concat 370693739@qq.com
 **/
@Component
@Slf4j
public class FallbackHandler {

    public Mono<ServerResponse> fall(ServerRequest request) {
        log.info("request header size: {}", request.headers().header("Test-Header").size());
        log.info("request header size: {}", request.headers().header("Execution-Exception-Message").size());
        return ServerResponse.ok().body(Mono.just("fallback"), String.class);
    }
}
