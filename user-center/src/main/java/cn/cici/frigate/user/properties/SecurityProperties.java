package cn.cici.frigate.user.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @createDate:2019/5/7$12:10$
 * @author: Heyfan Xie
 */
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private Access access;

    private Refresh refresh;

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public Refresh getRefresh() {
        return refresh;
    }

    public void setRefresh(Refresh refresh) {
        this.refresh = refresh;
    }

    public static class Access {

        private Integer expires;

        private boolean enableRefresh;

        public boolean isEnableRefresh() {
            return enableRefresh;
        }

        public void setEnableRefresh(boolean enableRefresh) {
            this.enableRefresh = enableRefresh;
        }

        public Integer getExpires() {
            return expires;
        }

        public void setExpires(Integer expires) {
            this.expires = expires;
        }
    }

    public static class Refresh {
        private Integer expires;

        public Integer getExpires() {
            return expires;
        }

        public void setExpires(Integer expires) {
            this.expires = expires;
        }
    }

}
