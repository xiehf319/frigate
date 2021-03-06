package cn.cici.auth.server.client;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.component.vo.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @createDate:2019/7/9$16:01$
 * @author: Heyfan Xie
 */
@FeignClient("rbac-service")
public interface UserClient {

    @RequestMapping(value = "/api/rbac/user/find-by-name", method = RequestMethod.GET)
    R<UserInfo> findByUsername(@RequestParam("username") String username);
}
