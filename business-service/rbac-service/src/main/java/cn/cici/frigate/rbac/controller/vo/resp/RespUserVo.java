package cn.cici.frigate.rbac.controller.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @createDate:2019/7/9$15:58$
 * @author: Heyfan Xie
 */
@Data
@ApiModel("用户")
public class RespUserVo {

    @ApiModelProperty("用户id")
    private long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("是否锁定")
    private boolean locked;

    @ApiModelProperty("是否可用")
    private boolean enable;
}
