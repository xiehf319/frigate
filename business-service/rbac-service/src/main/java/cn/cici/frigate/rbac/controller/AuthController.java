package cn.cici.frigate.rbac.controller;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.rbac.dao.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @createDate:2019/7/9$14:46$
 * @author: Heyfan Xie
 */
@RestController
@Api(tags = {"鉴权管理"})
public class AuthController {

    @ApiOperation("根据用户名查询用户")
    @PostMapping("/rbac/user/find-by-name")
    public R<User> findByUserName(@RequestParam("username") String username) {
        return null;
    }
}
