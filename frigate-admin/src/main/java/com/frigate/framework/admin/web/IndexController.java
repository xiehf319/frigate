package com.frigate.framework.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @createDate:2019/4/24$14:08$
 * @author: Heyfan Xie
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model) {
        List<String> menus = new ArrayList<>();
        menus.add("a");
        menus.add("b");
        menus.add("c");
        menus.add("d");
        model.addAttribute("menus", menus);
        return "pages/index";
    }
}
