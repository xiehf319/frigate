package cn.cici.frigate.sso.forth.config;

import cn.cici.frigate.sso.forth.filter.AuthInterceptor;
import cn.cici.frigate.sso.forth.filter.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/24 10:55
 * @author: Heyfan Xie
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor interceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/login");
    }
}
