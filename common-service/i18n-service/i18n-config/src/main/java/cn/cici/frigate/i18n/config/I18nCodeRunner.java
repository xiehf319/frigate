package cn.cici.frigate.i18n.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 15:39
 * @author: Heyfan Xie
 */
@Configuration
public class I18nCodeRunner implements CommandLineRunner {

    @Autowired
    private I18nContext i18nContext;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("启动完成了");
        i18nContext.init();
    }
}
