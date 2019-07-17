package cn.cici.frigate.rbac.controller.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/17 15:57
 * @author: Heyfan Xie
 */
@Data
public class ReqUserVo {

    @NotNull
    private String username;

    @NotBlank
    private String mobile;

    @NotEmpty
    private String password;
}
