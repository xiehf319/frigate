//package cn.cici.frigate.gateway.config;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletWebRequest;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * @description:
// * @createDate:2019/4/29$17:10$
// * @author: Heyfan Xie
// */
//@Configuration
//public class FeignHeaderInterceptor implements RequestInterceptor {
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        ServletWebRequest request = getHttpServletRequest();
//        if (Objects.isNull(request)) {
//            return;
//        }
//        Map<String, String> headers = getHeaders(request);
//        if (headers.size() > 0) {
//            Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> entry = iterator.next();
//                requestTemplate.header(entry.getKey(), entry.getValue());
//            }
//        }
//    }
//
//    private ServletWebRequest getHttpServletRequest() {
//        try {
//            ServletWebRequest servletWebRequest = (ServletWebRequest)(RequestContextHolder.currentRequestAttributes());
//            return servletWebRequest;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private Map<String, String> getHeaders(ServletWebRequest request) {
//
//        Iterator<String> enums = request.getHeaderNames();
//        Map<String, String> map = new HashMap<>();
//
//        while (enums.hasNext()) {
//            String key = enums.next();
//            String value = request.getHeader(key);
//            map.put(key, value);
//        }
//        return map;
//    }
//}
