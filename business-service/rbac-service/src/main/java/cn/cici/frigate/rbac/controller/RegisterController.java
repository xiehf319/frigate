package cn.cici.frigate.rbac.controller;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.rbac.controller.vo.req.ReqEmailRegisterVo;
import cn.cici.frigate.rbac.controller.vo.req.ReqMobileRegisterVo;
import cn.cici.frigate.rbac.controller.vo.req.ReqSystemRegisterVo;
import cn.cici.frigate.rbac.service.RegisterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/18 14:30
 * @author: Heyfan Xie
 */
@RestController
@RequestMapping("/rbac/register")
public class RegisterController {


    @Autowired
    private RegisterService registerService;

    /**
     * 用户名密码注册
     *
     * @param registerVo
     * @return
     */
    @PostMapping("/username")
    @ApiOperation("用户名密码注册")
    public R userRegister(@RequestBody ReqSystemRegisterVo registerVo) {
        registerService.systemRegister(registerVo.getUsername(), registerVo.getPassword());
        return R.success();
    }


    /**
     * 手机验证码注册
     *
     * @param registerVo
     * @return
     */
    @PostMapping("/mobile")
    @ApiOperation("手机验证码注册")
    public R mobileRegister(@RequestBody ReqMobileRegisterVo registerVo) {
        registerService.mobileRegister(registerVo.getMobile(), registerVo.getSmsCode(), registerVo.getPassword());
        return R.success();
    }

    /**
     * 邮箱注册
     *
     * @param registerVo
     * @return
     */
    @PostMapping("/email")
    @ApiOperation("邮箱注册")
    public R mobileRegister(@RequestBody ReqEmailRegisterVo registerVo) {
        registerService.emailRegister(registerVo.getEmail(), registerVo.getSmsCode(), registerVo.getPassword());
        return R.success();
    }
}
