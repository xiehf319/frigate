package cn.cici.frigate.rbac.controller;

import cn.cici.frigate.component.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @createDate:2019/5/9$11:42$
 * @author: Heyfan Xie
 */
@RestController
@Api(tags = {"菜单管理"})
public class MenuController {

    @ApiOperation("测试查询一下")
    @GetMapping("/menu")
    public R<String> getMenu() {
        return R.success("test");
    }
}
