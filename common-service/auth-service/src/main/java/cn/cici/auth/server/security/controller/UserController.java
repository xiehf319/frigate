package cn.cici.auth.server.security.controller;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.component.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @description:
 * @createDate:2019/5/6$10:43$
 * @author: Heyfan Xie
 */
@RestController
@Slf4j
public class UserController {

    @RequestMapping(value = "/auth/current", method = RequestMethod.GET)
    public R<UserInfo> getUser(Principal principal) {
        UserInfo user = (UserInfo) principal;
        log.info(user.toString());
        return R.success(user);
    }
}
