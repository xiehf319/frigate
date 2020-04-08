package com.cici.i18n.ui.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 类描述:
 *  页面跳转控制
 * @author 003300
 * @version 1.0
 * @date 2019/9/26 13:38
 */
@Controller
public class PageController {

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "baidu")
    public ModelAndView baidu() {
        return new ModelAndView("redirect:http://baidu.com");
    }
}
