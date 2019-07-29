package cn.cici.frigate.rbac.controller;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.rbac.constant.RbacResponseEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 15:43
 * @author: Heyfan Xie
 */
@RestController
public class CodeTestController {

    @RequestMapping("/test1")
    public R test1() {
        RbacResponseEnum.TEST1.assertNotEmpty("");
        return R.success();
    }

    @RequestMapping("/test2")
    public R test2() {
        RbacResponseEnum.TEST2.assertNotEmpty("");
        return R.success();
    }
}
