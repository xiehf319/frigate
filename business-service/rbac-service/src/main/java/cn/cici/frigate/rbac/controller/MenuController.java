package cn.cici.frigate.rbac.controller;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.id.client.UidProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @createDate:2019/5/9$11:42$
 * @author: Heyfan Xie
 */
@RestController
@Api(tags = {"菜单管理"})
public class MenuController {

    @Autowired
    UidProvider uidProvider;

    @ApiOperation("测试查询一下")
    @GetMapping("/rbac/menu")
    public R<String> getMenu(@RequestHeader("X-USER-ID") String id, @RequestHeader("X-USER-NAME") String username) {
        for (int i = 0; i < 100; i++) {
            System.out.println(uidProvider.getId());
        }
        return R.success("test " + id + " " + username);
    }
}
