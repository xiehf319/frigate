//package cn.cici.frigate.redis.config;
//
//import cn.cici.frigate.redis.config.test.Bean1;
//import cn.cici.frigate.redis.config.test.Bean2;
//import cn.cici.frigate.redis.config.test.Bean3;
//import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
///**
// * @description:
// * @createDate:2019/5/30$17:33$
// * @author: Heyfan Xie
// */
//@SpringBootTest
//public class ConfigTest {
//
//    @Test
//    public void testBean() {
//
//
//        ApplicationContext context = new AnnotationConfigApplicationContext(ImportTest.class);
//
//        Bean1 bean1 = context.getBean(Bean1.class);
//
//        Bean2 bean2 = context.getBean(Bean2.class);
//
//        Bean3 bean3 = context.getBean(Bean3.class);
//
//        bean1.execute();
//        bean2.execute();
//        bean3.execute();
//    }
//}
