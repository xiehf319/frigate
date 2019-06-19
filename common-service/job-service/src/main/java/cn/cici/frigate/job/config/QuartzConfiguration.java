package cn.cici.frigate.job.config;

import cn.cici.frigate.job.factory.SpringJobFactory;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @description:
 * https://www.jianshu.com/p/95c889861376
 * @createDate:2019/6/19$16:31$
 * @author: Heyfan Xie
 *
 */
@Configuration
@Slf4j
public class QuartzConfiguration {

    @Autowired
    private SpringJobFactory springJobFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        final SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(springJobFactory);
        schedulerFactoryBean.setDataSource(dataSource);
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

}
