package cn.cici.frigate.i18n.config;


import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author xiehaifan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
@Import(I18nConfigRegistrar.class)
public @interface EnableI18nConfig {

    @AliasFor(value = "serviceId")
    String value() default "";

    @AliasFor(value = "value")
    String serviceId() default "";
}
