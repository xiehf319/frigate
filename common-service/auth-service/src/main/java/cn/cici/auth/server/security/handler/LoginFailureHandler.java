package cn.cici.auth.server.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiehf
 * @date 2019/5/13 23:10
 * @concat 370693739@qq.com
 **/
@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        request.setAttribute("exception", exception.getMessage());
        request.setAttribute("username", request.getParameter("username"));
        request.getRequestDispatcher("/login?error").forward(request, response);
    }
}
