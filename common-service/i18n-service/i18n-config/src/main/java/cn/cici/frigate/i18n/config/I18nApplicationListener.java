package cn.cici.frigate.i18n.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 15:15
 * @author: Heyfan Xie
 */
public class I18nApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("启动了");
    }
}
