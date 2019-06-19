package cn.cici.frigate.rbac.controller;

import cn.cici.frigate.rbac.service.propagation.required.RequiredService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试事务 隔离级别
 * @createDate:2019/6/14$13:54$
 * @author: Heyfan Xie
 */
@RestController
@Api(tags = "事务隔离级别测试")
@RequestMapping("/rbac")
public class PropagationController {

    @Autowired
    private RequiredService example1Service;

    @GetMapping("/propagation1")
    @ApiOperation("111111")
    public String test1() {
        example1Service.notransactionExceptionRequiredRequried();
        return "success";
    }

    @GetMapping("/propagation2")
    @ApiOperation("222222222")
    public String test2() {
        example1Service.notransactionequiredRequriedException();
        return "success";
    }

    @GetMapping("/propagation3")
    @ApiOperation("3333333333")
    public String test3() {
        example1Service.transactionExceptionRequiredRequired();
        return "success";
    }

    @GetMapping("/propagation4")
    @ApiOperation("4444444")
    public String test4() {
        example1Service.transactionRequiredRequiredException();
        return "success";
    }

    @GetMapping("/propagation5")
    @ApiOperation("555555555")
    public String test5() {
        example1Service.transactionRequiredRequiredExceptionTry();
        return "success";
    }
}
