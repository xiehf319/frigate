package cn.cici.frigate.i18n.config;

import cn.cici.frigate.component.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiehf
 * @description: 类介绍:
 * @date 2019/7/28 14:24
 * @concat 370693739@qq.com
 **/
@Component
public class I18nContext {

    private static ConcurrentHashMap<String, ConcurrentHashMap<String, String>> CACHE;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String applicationName;

    public void init() {
        R<Map<String, LinkedHashMap<String, String>>> result = restTemplate.getForObject("http://I18N-SERVICE/i18n/status/code/" + applicationName, R.class);
        Map<String, LinkedHashMap<String, String>> data = result.getData();
        CACHE = new ConcurrentHashMap<>();
        data.forEach((k, v) -> {
            CACHE.put(k, new ConcurrentHashMap<>(v));
        });
    }

    /**
     * 单语言获取所有的
     * @param lang
     * @return
     */
    public static ConcurrentHashMap<String, String> getByLang(String lang) {
        return CACHE.get(lang);
    }

    /**
     *
     * @param lang
     * @param code
     * @return
     */
    public static String getByLangAndCode(String lang, String code) {
        ConcurrentHashMap<String, String> stringStringLinkedHashMap = CACHE.get(lang);
        if (stringStringLinkedHashMap == null) {
            return null;
        }
        return stringStringLinkedHashMap.get(code);
    }
}
