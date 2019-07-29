package cn.cici.frigate.i18n.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @description:
 * @createDate:2019/6/20$12:04$
 * @author: Heyfan Xie
 */
@Configuration
public class LocaleConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        return localeResolver;
    }

    /**
     * 默认拦截器 lang 为切换语言的参数
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer localInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
                localeChangeInterceptor.setParamName("lang");
                registry.addInterceptor(localeChangeInterceptor);
            }
        };
    }

    @Bean
    public MessageUtils messageUtils() {
        System.out.println("创建 MessageUtils");
        return new MessageUtils();
    }
}
