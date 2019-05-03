package cn.cici.frigate.gateway.properties;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author xiehf
 * @date 2019/5/3 15:00
 * @concat 370693739@qq.com
 **/
public class PermitAllUrlProperties {

    private static List<Pattern> permitAllUrlPatterns;

    private List<Url> permitAllUrls;

    public String[] getPermitAllPattherns() {
        List<String> urls = new ArrayList<>();
        Iterator<Url> iterator = permitAllUrls.iterator();
        while (iterator.hasNext()) {
            urls.add(iterator.next().getPattern());
        }
        return urls.toArray(new String[0]);
    }

    public static class Url {

        private String pattern;

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
    }
    @PostConstruct
    public void init() {
        if (permitAllUrls != null && permitAllUrls.size() > 0) {
            permitAllUrlPatterns = new ArrayList<>();
            Iterator<Url> iterator = permitAllUrls.iterator();
            while (iterator.hasNext()) {
                String currentUrl = iterator.next().getPattern();
                Pattern currentPattern = Pattern.compile(currentUrl);
                permitAllUrlPatterns.add(currentPattern);
            }
        }
    }

    public boolean isPermitAllUrl(String url) {
        for (Pattern pattern : permitAllUrlPatterns) {
            if (pattern.matcher(url).find()) {
                return  true;
            }
        }
        return false;
    }
}
