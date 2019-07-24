package cn.cici.frigate.sso.third.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/23 15:40
 * @author: Heyfan Xie
 */
@Component
public class PostFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getHeader("token") == null) {
            resp.setStatus(401);
        } else {
            chain.doFilter(request, response);
        }
    }
}
