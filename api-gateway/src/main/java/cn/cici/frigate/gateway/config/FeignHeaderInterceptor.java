package cn.cici.frigate.gateway.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @description:
 * @createDate:2019/4/29$17:10$
 * @author: Heyfan Xie
 */
@Configuration
public class FeignHeaderInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = getHttpServletRequest();
        if (Objects.isNull(request)) {
            return;
        }
        Map<String, String> headers = getHeaders(request);
        if (headers.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                requestTemplate.header(entry.getKey(), entry.getValue());
            }
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {

        Enumeration<String> enums = request.getHeaderNames();
        Map<String, String> map = new HashMap<>();

        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
