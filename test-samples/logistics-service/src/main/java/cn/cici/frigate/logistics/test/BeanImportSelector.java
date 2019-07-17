package cn.cici.frigate.logistics.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @createDate:2019/5/30$17:05$
 * @author: Heyfan Xie
 */
public class BeanImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"cn.cici.frigate.logistics.test.Bean2"};
    }
}
