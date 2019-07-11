package cn.cici.auth.server.security.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author xiehf
 * @date 2019/5/13 23:10
 * @concat 370693739@qq.com
 **/
@Component
@Slf4j
public class SmsCodeLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String INNER_PAGE_TYPE = "text/html";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("登陆成功");
        String type = request.getHeader("Accept");
        log.info("Accept: {}", type);

        // 不通过内置登陆页面请求的
        if (!type.contains(INNER_PAGE_TYPE)) {
            String clientId = request.getParameter("clientId");
            log.info("clientId: {}", clientId);
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
            // todo 此处校验client是否有效

            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(16), clientId, clientDetails.getScope(), "mobile");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

            // 生成token
            OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

            // 设置为json返回
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

            // 响应结果
            response.getWriter().write(objectMapper.writeValueAsString(accessToken));
        } else {
            // 通过内置页面请求，不做任何处理
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
