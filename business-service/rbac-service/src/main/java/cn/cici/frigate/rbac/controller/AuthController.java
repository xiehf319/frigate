package cn.cici.frigate.rbac.controller;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.rbac.controller.vo.resp.RespUserVo;
import cn.cici.frigate.rbac.dao.entity.User;
import cn.cici.frigate.rbac.dao.entity.UserAuth;
import cn.cici.frigate.rbac.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @createDate:2019/7/9$14:46$
 * @author: Heyfan Xie
 */
@RestController
@Api(tags = {"鉴权管理"})
@RequestMapping("/api")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;


    @ApiOperation("根据用户名查询用户")
    @GetMapping("/rbac/user/find-by-name")
    public R<RespUserVo> findByUserName(@RequestParam("username") String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            log.info("用户不存在 {}", username);
            return R.success();
        }
        UserAuth userAuth = userService.findByUserIdAndType(user.getId(), "SYSTEM");
        if (userAuth == null) {
            log.info("用户没有创建账号 {}", username);
            return R.success();
        }
        RespUserVo userVo = new RespUserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setPassword(userAuth.getCredential());
        userVo.setLocked(user.getLocked());
        userVo.setEnable(user.getEnabled());
        return R.success(userVo);
    }




}
