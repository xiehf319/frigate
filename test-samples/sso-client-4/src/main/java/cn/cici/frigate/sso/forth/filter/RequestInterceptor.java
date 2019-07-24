package cn.cici.frigate.sso.forth.filter;

import cn.cici.frigate.sso.forth.UserInfo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/24 9:55
 * @author: Heyfan Xie
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("2请求开始： " + request.getRequestURI());
        if (request.getSession().getAttribute("user") != null) {
            return true;
        }
        String state = request.getParameter("state");
        String uri = getUri(request);
        String code = request.getParameter("code");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("grant_type", "authorization_code");
        map.add("client_id", "forth");
        map.add("client_secret", "123456");
        map.add("redirect_uri", URLEncoder.encode("http://localhost:9906", "UTF-8"));
        ResponseEntity<String> resp = restTemplate.postForEntity("http://localhost:4000/oauth/token", new HttpEntity<>(map, headers), String.class);
        System.out.println(resp.getBody());
        if (resp.getStatusCode() == HttpStatus.OK) {
            String body = resp.getBody();
            JSONObject jsonObject = JSONObject.parseObject(body);
            String token = jsonObject.getString("access_token");

            HttpHeaders h = new HttpHeaders();
            h.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            h.set("Authorization", "Bearer " + token);

            ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:4000/oauth/user", new HttpEntity<>(h), String.class);
            System.out.println(res.getBody());
            UserInfo user = new UserInfo("admin");
            request.getSession().setAttribute("user", user);
            return false;
        }
        request.getSession().setAttribute(state, uri);
        String redirectUri = buildAuthCodeUri(uri, state);
        response.sendRedirect(redirectUri);
        return true;
    }

    private String buildAuthCodeUri(String uri, String state) {
        System.out.println(uri);
        System.out.println(state);
        return uri;
    }

    private String getUri(HttpServletRequest request) {
        return request.getRequestURI();
    }

    private boolean isLoginFromSSO(String state) {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("请求结束");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
