package cn.cici.auth.server.constant;

/**
 * @description:
 * @createDate:2019/7/9$16:33$
 * @author: Heyfan Xie
 */
public interface SecurityConstants {

    String USER_HEADER = "x-user-header";

    String ROLE_HEADER = "x-role-header";

    String AUTHENTICATION_CODE = "authentication_code";

    String PASSWORD = "password";

    String REFRESH_TOKEN = "refresh_token";

    String OAUTH_TOKEN_URL = "/oauth/token";

    String MOBILE_TOKEN_URL = "/mobile/token";


}
