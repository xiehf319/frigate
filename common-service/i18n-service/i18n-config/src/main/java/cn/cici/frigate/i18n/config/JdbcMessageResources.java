package cn.cici.frigate.i18n.config;

import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 14:06
 * @author: Heyfan Xie
 */
public class JdbcMessageResources implements MessageResource {

    /**
     * 有默认值的获取文案
     *
     * @param code
     * @param args
     * @param defaultMessage
     * @param locale
     * @return
     */
    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {

        String message = I18nContext.getByLangAndCode(locale.toString(), code);
        if (message == null) {
            return defaultMessage;
        }
        return String.format(message, args);
    }

    /**
     * 没有默认值的获取文案
     *
     * @param code
     * @param args
     * @param locale
     * @return
     * @throws NoSuchMessageException
     */
    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        String message = I18nContext.getByLangAndCode(locale.toString(), code);
        if (message == null) {
            return null;
        }
        return String.format(message, args);
    }
}
