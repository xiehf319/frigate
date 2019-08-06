package cn.cici.frigate.rbac.controller;

import cn.cici.frigate.component.vo.R;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @description:
 * @createDate:2019/7/9$14:17$
 * @author: Heyfan Xie
 */
@RestController
@Api(tags = {"用户管理"})
public class UserController {

    @PostMapping(value = "/rbac/xml", headers = {"content-type=application/xml"})
    public R xml(@RequestBody UserXml userXml) {
        System.out.println(userXml);
        return R.success();
    }

}


@XmlRootElement(name = "xml")
@Data
class UserXml {


    private String username;

    private String responseType;

    @XmlElement(name = "username")
    public String getUsername() {
        return username;
    }

    @XmlElement(name = "response_type")
    public String getResponseType() {
        return responseType;
    }
}