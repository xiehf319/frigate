package cn.cici.frigate.fescar.config;

import cn.cici.frigate.fescar.filter.FescarRMRequestFilter;
import cn.cici.frigate.fescar.interceptor.FescarRestInterceptor;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fescar.rm.datasource.DataSourceProxy;
import com.alibaba.fescar.spring.annotation.GlobalTransactionScanner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * @description:
 * @createDate:2019/5/9$11:15$
 * @author: Heyfan Xie
 */
@Configuration
public class FescarAutoConfiguration {

    public static final String FESCAR_XID = "FESCAR-XID";

    @Bean
    public DataSource dataSource(Environment environment) {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        try {
            dataSource.setDriver(DriverManager.getDriver(environment.getProperty("spring.datasource.url")));
        } catch (SQLException e) {
            throw new RuntimeException("can't recognize datasource Driver.");
        }
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return new DataSourceProxy(dataSource);
    }

    @Bean
    public GlobalTransactionScanner globalTransactionScanner(Environment environment) {
        String applicationName = environment.getProperty("spring.application.name");
        String groupName = environment.getProperty("fescar.group.name");
        if (applicationName == null) {
            return new GlobalTransactionScanner(groupName == null ? "my_test_tx_group" : groupName);
        }
        return new GlobalTransactionScanner(applicationName, groupName == null ? "my_test_tx_group" : groupName);
    }

    @ConditionalOnBean({RestTemplate.class})
    @Bean
    public Object addFescarInterceptor(Collection<RestTemplate> restTemplates) {
        restTemplates.stream()
                .forEach(restTemplate -> {
                    List<ClientHttpRequestInterceptor> interceptorList = restTemplate.getInterceptors();
                    if (interceptorList != null) {
                        interceptorList.add(fescarRestInterceptor());
                    }
                });
        return new Object();
    }

    @Bean
    public FescarRMRequestFilter fescarRMRequestFilter() {
        return new FescarRMRequestFilter();
    }

    @Bean
    public FescarRestInterceptor fescarRestInterceptor(){
        return new FescarRestInterceptor();
    }
}
