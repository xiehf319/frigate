package cn.cici.frigate.rbac.controller.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 15:57
 * @author: Heyfan Xie
 */
@Data
@ApiModel("用户名密码注册对象")
public class ReqSystemRegisterVo {

    @NotEmpty
    @Size(max = 30, min = 6)
    @ApiModelProperty("用户名")
    private String username;

    @NotEmpty
    @Size(max = 20, min = 6)
    @ApiModelProperty("密码")
    private String password;
}
