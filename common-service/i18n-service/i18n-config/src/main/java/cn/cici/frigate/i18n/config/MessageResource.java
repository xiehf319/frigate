package cn.cici.frigate.i18n.config;

import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;

import java.util.Locale;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 14:05
 * @author: Heyfan Xie
 */
public interface MessageResource {

    @Nullable
    String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage, Locale locale);

    String getMessage(String code, @Nullable Object[] args, Locale locale) throws NoSuchMessageException;
}
