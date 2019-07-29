package cn.cici.frigate.i18n.manager.controller;

import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.i18n.manager.controller.vo.req.ReqServiceLangVo;
import cn.cici.frigate.i18n.manager.jpa.entity.CodeMessage;
import cn.cici.frigate.i18n.manager.service.StatusCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 10:49
 * @author: Heyfan Xie
 */
@RestController
@Api(tags = {"状态码控制器"})
public class StatusCodeController {

    @Autowired
    private StatusCodeService statusCodeService;

    @PostMapping("/i18n/status/code")
    @ApiOperation("新增状态码")
    public R addCode(@Valid @RequestBody ReqServiceLangVo codeVo) {
        statusCodeService.save(codeVo);
        return R.success();
    }

    @GetMapping("/i18n/status/code/{serviceName}")
    @ApiOperation("根据服务名查找所有的语言状态码信息")
    public R<Map<String, LinkedHashMap<String, String>>> findByServiceName(@PathVariable String serviceName){
        return R.success(statusCodeService.findByServiceName(serviceName));
    }
}
