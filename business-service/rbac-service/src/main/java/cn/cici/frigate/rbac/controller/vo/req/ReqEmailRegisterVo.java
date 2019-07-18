package cn.cici.frigate.rbac.controller.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 15:57
 * @author: Heyfan Xie
 */
@Data
@ApiModel("邮箱密码注册对象")
public class ReqEmailRegisterVo {

    @NotEmpty
    @Email
    @ApiModelProperty("用户名")
    private String email;

    @NotEmpty
    @Size(max = 6, min = 6)
    @ApiModelProperty("验证码")
    private String smsCode;

    @NotEmpty
    @Size(max = 20, min = 6)
    @ApiModelProperty("密码")
    private String password;
}
