package cn.cici.frigate.component.annotation;

import cn.cici.frigate.component.scanner.LogCollectorScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiehf
 * @date 2019/6/20 22:26
 * @concat 370693739@qq.com
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(LogCollectorScannerRegistrar.class)
public @interface EnableLogCollector {

    String[] basePackages() default {""};
}
