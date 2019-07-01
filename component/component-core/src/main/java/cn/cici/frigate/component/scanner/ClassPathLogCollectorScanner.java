package cn.cici.frigate.component.scanner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

import java.util.Arrays;
import java.util.Set;

/**
 * @author xiehf
 * @date 2019/6/20 22:33
 * @concat 370693739@qq.com
 **/
@Slf4j
public class ClassPathLogCollectorScanner extends ClassPathBeanDefinitionScanner {

    public ClassPathLogCollectorScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public ClassPathLogCollectorScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    public ClassPathLogCollectorScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment) {
        super(registry, useDefaultFilters, environment);
    }

    public ClassPathLogCollectorScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters, Environment environment, ResourceLoader resourceLoader) {
        super(registry, useDefaultFilters, environment, resourceLoader);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if (beanDefinitionHolders.isEmpty()) {
            log.warn("No log collector Spring component was found in {}",
                    Arrays.toString(basePackages));
        }
        return beanDefinitionHolders;
    }
}
