package cn.cici.frigate.fescar.interceptor;

import cn.cici.frigate.fescar.config.FescarAutoConfiguration;
import com.alibaba.fescar.core.context.RootContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/16 13:58
 * @author: Heyfan Xie
 */
public class FescarRestInterceptor implements ClientHttpRequestInterceptor, RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {
        String xid = RootContext.getXID();
        if (StringUtils.isNotEmpty(xid)) {
            requestTemplate.header(FescarAutoConfiguration.FESCAR_XID, xid);
        }
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String xid = RootContext.getXID();
        if (StringUtils.isNotEmpty(xid)) {
            HttpHeaders headers = request.getHeaders();
            headers.add(FescarAutoConfiguration.FESCAR_XID, xid);
        }
        return execution.execute(request, body);
    }
}
