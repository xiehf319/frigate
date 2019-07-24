package cn.cici.frigate.sso.third.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/23 11:40
 * @author: Heyfan Xie
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/list")
    public String list() {
        return "member/list";
    }

    @GetMapping("/info")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }

    @GetMapping("/me")
    @ResponseBody
    public String me() {
        return "哈哈哈哈";
    }

}
