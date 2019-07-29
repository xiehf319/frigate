package cn.cici.frigate.i18n.manager.controller.vo.req;

import lombok.Data;

import java.util.List;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/29 10:50
 * @author: Heyfan Xie
 */
@Data
public class ReqServiceLangVo {

    private String serviceName;

    private String code;

    private List<ReqCodeMessageVo> messageList;
}
