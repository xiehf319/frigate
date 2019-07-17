package cn.cici.frigate.rbac.controller;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.rbac.controller.vo.req.ReqUserVo;
import cn.cici.frigate.redis.services.RedisStringServices;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description:
 * @createDate:2019/7/9$14:17$
 * @author: Heyfan Xie
 */
@RestController
@Api(tags = {"用户管理"})
public class UserController {
//
//    @Autowired
//    private RedisStringServices redisStringServices;
//
//    @RequestMapping("/user/info")
//    public R save(@Valid @RequestBody ReqUserVo userVo) {
//        System.out.println(userVo.toString());
//        redisStringServices.exists("aaa");
//        return R.success();
//    }
}
