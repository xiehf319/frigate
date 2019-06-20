package cn.cici.frigate.component.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @description:
 * @createDate:2019/6/20$12:11$
 * @author: Heyfan Xie
 */
@Slf4j
public class MessageUtils {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String result, Object[] params) {
        String message = "";
        try {
            Locale locale = LocaleContextHolder.getLocale();
            message = messageSource.getMessage(result, params, locale);
        } catch (Exception e) {
            log.error("parser message error!", e);
        }
        return message;
    }

}
