//package cn.cici.frigate.redis.config;
//
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.RootBeanDefinition;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.type.AnnotationMetadata;
//
///**
// * @description:
// * @createDate:2019/5/30$17:06$
// * @author: Heyfan Xie
// */
//public class MyImportBeanDeinitionRegistry implements ImportBeanDefinitionRegistrar {
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
//        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Bean3.class);
//        registry.registerBeanDefinition("bean3", rootBeanDefinition);
//    }
//}
