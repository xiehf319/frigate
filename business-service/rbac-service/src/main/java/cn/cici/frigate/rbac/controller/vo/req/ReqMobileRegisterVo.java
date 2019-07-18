package cn.cici.frigate.rbac.controller.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 15:57
 * @author: Heyfan Xie
 */
@Data
@ApiModel("手机号密码注册对象")
public class ReqMobileRegisterVo {

    @NotEmpty
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$")
    private String mobile;

    @NotEmpty
    @Size(max = 6, min = 6)
    @ApiModelProperty("验证码")
    private String smsCode;

    @NotEmpty
    @Size(max = 20, min = 6)
    @ApiModelProperty("密码")
    private String password;
}
