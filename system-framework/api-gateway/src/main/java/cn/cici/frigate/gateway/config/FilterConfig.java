package cn.cici.frigate.gateway.config;

import cn.cici.frigate.gateway.filter.HeaderEnhancerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/12 17:29
 * @author: Heyfan Xie
 */
@Configuration
@EnableAutoConfiguration
public class FilterConfig {

    @Autowired
    private HeaderEnhancerFilter headerEnhancerFilter;

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(headerEnhancerFilter);
        registrationBean.setOrder(0);
        return registrationBean;
    }

    @Bean
    @ConditionalOnMissingClass
    public HeaderEnhancerFilter headerEnhancerFilter() {
        return new HeaderEnhancerFilter();
    }
}
