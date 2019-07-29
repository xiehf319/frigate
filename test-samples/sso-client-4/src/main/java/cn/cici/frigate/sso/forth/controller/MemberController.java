package cn.cici.frigate.sso.forth.controller;

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
@RequestMapping("")
public class MemberController {

    @GetMapping("/member/list")
    public String list() {
        return "member/list";
    }

    @GetMapping("/member/info")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }


    @GetMapping("/member/me")
    @ResponseBody
    public String me() {
        return "哈哈哈哈";
    }


    @GetMapping("/login")
    public Object login() {
        return "aaaa";
    }
}
