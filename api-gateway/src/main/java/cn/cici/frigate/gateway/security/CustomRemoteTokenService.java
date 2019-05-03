package cn.cici.frigate.gateway.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiehf
 * @date 2019/5/2 15:55
 * @concat 370693739@qq.com
 **/
public class CustomRemoteTokenService {

    private LoadBalancerClient loadBalancerClient;

    private final static Logger LOGGER = LoggerFactory.getLogger(CustomRemoteTokenService.class);

    private RestTemplate restTemplate;

    private String checkTokenEndpointUrl;


    private String clientId;

    private String clientSecret;

    private String tokenName = "token";

    private String ERROR = "error";

    public CustomRemoteTokenService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400) {
                    super.handleError(response);
                }
            }
        });
    }

    public void setCheckTokenEndpointUrl(String checkTokenEndpointUrl) {
        this.checkTokenEndpointUrl = checkTokenEndpointUrl;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public void loadAuthentication(String accessToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add(tokenName, accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, getAuthorizationHeader(clientId, clientSecret));

        ServiceInstance serviceInstance = loadBalancerClient.choose("auth-center");
        if (serviceInstance == null) {
            throw new RuntimeException("Failed to choose an auth instance");
        }

        Map<String, Object> map = postForMap(serviceInstance.getUri().toString() + checkTokenEndpointUrl, formData, headers);
        if (map.containsKey(ERROR)) {
            LOGGER.debug("check_token returned error: " + map.get(ERROR));
            Object status = map.get("status");
            if (status != null && status.equals(HttpStatus.BAD_REQUEST.value())) {
                throw new RuntimeException();
            }
            HttpStatus code = (HttpStatus) map.get(ERROR);
            if (code == HttpStatus.UNAUTHORIZED) {
                throw new RuntimeException();
            } else {
                throw new RuntimeException();
            }
        }
        Assert.state(map.containsKey("client_id"), "client_id must be present");
    }

    public void setLoadBalancerClient(LoadBalancerClient loadBalancerClient) {
        this.loadBalancerClient = loadBalancerClient;
    }

    private String getAuthorizationHeader(String clientId, String clientSecret) {
        String creds = String.format("%s:%s", clientId, clientSecret);
        return "Basic " + new String(Base64.getEncoder().encode(creds.getBytes(Charset.forName("UTF-8"))));
    }


    private Map<String, Object> postForMap(String path, MultiValueMap<String, String> formData, HttpHeaders headers) {
        if (headers.getContentType() == null) {
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
        Map<String, Object> map = new HashMap<>();
        try {
            map = restTemplate.exchange(path, HttpMethod.POST,
                    new HttpEntity<>(formData, headers), Map.class).getBody();
        } catch (HttpClientErrorException e1) {
            LOGGER.error("catch token exception when check token !", e1);
            map.put(ERROR, e1.getStatusCode());
        } catch (HttpServerErrorException e2) {
            LOGGER.error("catch no permission exception when check token", e2.getMessage());
            map.put(ERROR, e2.getStatusCode());
        } catch (Exception e) {
            LOGGER.error("catch common exception when check token!", e);
        }
        return map;
    }
}
