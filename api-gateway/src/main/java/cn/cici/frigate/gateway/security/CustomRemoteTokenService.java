package cn.cici.frigate.gateway.security;

import cn.cici.frigate.gateway.exception.ErrorCode;
import cn.cici.frigate.gateway.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @createDate:2019/5/5$10:55$
 * @author: Heyfan Xie
 */
@Slf4j
public class CustomRemoteTokenService {

    private LoadBalancerClient loadBalancerClient;

    private String checkTokenUri;

    private RestTemplate restTemplate;

    private static final String  ERROR = "error";

    public CustomRemoteTokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LoadBalancerClient getLoadBalancerClient() {
        return loadBalancerClient;
    }

    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    public String getCheckTokenUri() {
        return checkTokenUri;
    }

    public void setCheckTokenUri(String checkTokenUri) {
        this.checkTokenUri = checkTokenUri;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 检查token
     * 1. 直接调用auth-server
     * 2. 优化后要改成从缓存中获取
     *
     * @param accessToken
     */
    public void loadAuthentication(String accessToken) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("auth-server");
        if (serviceInstance == null) {
            throw new ServerException(HttpStatus.INTERNAL_SERVER_ERROR, new ErrorCode(500, "Server Error", "Server Error"));
        }

        Map<String, Object> map = postForMap(serviceInstance.getUri().toString() + checkTokenUri, accessToken);
        if (map.containsKey(ERROR)) {
            log.debug("check_token returned error" + map.get(ERROR));
            throw new ServerException(HttpStatus.BAD_REQUEST, new ErrorCode(400, "", ""));
        }
    }

    /***
     * 检查token 有效性
     *
     * @param path
     * @param accessToken
     * @return
     */
    private Map<String, Object> postForMap(String path, String accessToken) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Map map = new HashMap<>();
        try {
            String url = path + "?token=" + accessToken;
            map= restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers), Map.class).getBody();
        } catch (HttpClientErrorException e) {
            log.error("catch token exception when check token!", e);
            map.put(ERROR, "catch token exception when check token!");
        } catch (HttpServerErrorException e) {
            log.error("catch no permission exception when check token!", e);
            map.put(ERROR, "catch no permission exception when check token!");
        }
        Map<String, Object> result = map;
        return result;
    }
}
