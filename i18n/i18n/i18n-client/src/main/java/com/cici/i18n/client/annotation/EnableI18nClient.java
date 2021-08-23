package com.cici.i18n.client.annotation;

import com.cici.i18n.client.registrar.I18nConfigRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 类描述:
 *  注解启用i18n支持
 *
 * @version 1.0
 * @date 2019/9/16 14:33
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Documented
@Import(I18nConfigRegistrar.class)
public @interface EnableI18nClient {

    @AliasFor(value = "serviceId")
    String value() default "";

    String lang() default "zh_CN";

    @AliasFor(value = "value")
    String serviceId() default "";
}
