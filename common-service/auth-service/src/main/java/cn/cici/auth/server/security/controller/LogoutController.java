package cn.cici.auth.server.security.controller;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @createDate:2019/5/10$11:25$
 * @author: Heyfan Xie
 */
@Controller
public class LogoutController {

    @RequestMapping("/oauth/user/invoke")
    public void exit(HttpServletRequest request, HttpServletResponse response) {

        new SecurityContextLogoutHandler().logout(request, null, null);

        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
