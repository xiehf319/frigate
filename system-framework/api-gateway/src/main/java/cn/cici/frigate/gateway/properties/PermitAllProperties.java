package cn.cici.frigate.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @description:
 * @createDate:2019/5/25$15:09$
 * @author: Heyfan Xie
 */

@ConfigurationProperties(prefix = "security.oauth2")
@Component
@Data
public class PermitAllProperties {

    private static List<Pattern> permitUrlPattern;

    private List<Url> permitAll;

    public static List<Pattern> getPermitallUrlPattern() {
        return permitUrlPattern;
    }

    public String[] getPermitAllPatterns() {
        List<String> urls = new ArrayList<>();
        for (Url aPermitAll : permitAll) {
            urls.add(aPermitAll.getPattern());
        }
        return urls.toArray(new String[0]);
    }

    @PostConstruct
    public void init() {
        if (permitAll != null && permitAll.size() > 0) {
            permitUrlPattern = new ArrayList<>();
            for (Url permit : permitAll) {
                String currentUrl = permit.getPattern().replaceAll("\\*\\*", "(.*?)");
                Pattern currentPattern = Pattern.compile(currentUrl, Pattern.CASE_INSENSITIVE);
                permitUrlPattern.add(currentPattern);
            }

        }
    }

    public boolean isPermitAllUrl(String url) {
        for (Pattern pattern : permitUrlPattern) {
            if (pattern.matcher(url).find()) {
                return true;
            }
        }
        return false;
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
}
