package cn.cici.frigate.id.controller;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @createDate:2019/7/9$10:56$
 * @author: Heyfan Xie
 */
public interface UIdRemote {

    @GetMapping("/uid")
    long getId();
}
