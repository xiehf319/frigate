package cn.cici.frigate.rbac.controller;

import cn.cici.frigate.rbac.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @createDate:2019/5/9$13:44$
 * @author: Heyfan Xie
 */
@RestController
@Api(tags = {"测试事务管理"})
public class TransactionController {

    @Autowired
    private Trans0Service trans0Service;

    @Autowired
    private Trans1Service trans1Service;

    @Autowired
    private Trans2Service trans2Service;

    @Autowired
    private Trans3Service trans3Service;
    @Autowired
    private ITrans4Service trans4Service;


    @GetMapping("/test0")
    @ApiOperation("进入的方法没有注解事物，方法里面调用注解有事物注解的方法, this调用")
    public String test0() {
        trans0Service.add();
        return "进入service的方法 没有@Transactional，调用该service内部的任何方法都不会有事物, 事物不生效";
    }

    @GetMapping("test1")
    @ApiOperation("进入的方法没有注解事物，方法里面调用注解有事物注解的方法, 注入自身进行调用，没有实现接口注入自己")
    public String test2() {
        trans1Service.add();
        return "进入service的方法 没有@Transactional，该service没有通过实现接口，通过注入自身调用，事物独立";
    }

    @GetMapping("test2")
    @ApiOperation("进入的方法有注解事物，方法里面调用注解有事物注解的方法, 注解方式解决")
    public String test3() {
        trans2Service.add();
        return "进入service的方法 有@Transactional ，该service没有通过实现接口，调用的方法不管怎么注解事物级别，事物都会生效，全部回滚";
    }

    @GetMapping("test3")
    @ApiOperation("通过2个service调用")
    public String test4() {
        trans3Service.add();
        return "进入service的方法 没有有@Transactional ，调用另一个service，多个调用的service事物隔离，每个事物独立";
    }

    @GetMapping("test4")
    @ApiOperation("进入的方法没有注解事物，方法里面调用注解有事物注解的方法, 注入自身进行调用, 实现接口注入自己")
    public String test5() {
        trans4Service.add();
        return "进入service的方法 没有有@Transactional ，注入自身的接口实现类，通过诸如类调用自身的方法，每个有事物注解的方法事物独立";
    }
}
