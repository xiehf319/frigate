package cn.cici.frigate.sso.forth.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/24 9:55
 * @author: Heyfan Xie
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("1请求开始： " + request.getRequestURI());
//        if (request.getRequestURI().contains("login")) {
//            return true;
//        }
//        if (request.getSession().getAttribute("user") != null) {
//            return true;
//        }
//        String authorization = request.getHeader("Authorization");
//        if (authorization == null) {
//            response.setStatus(401);
//            return false;
//        }
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
