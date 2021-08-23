package cn.cici.frigate.rbac.controller.vo.resp;

import lombok.Data;

import java.util.List;


@Data
public class RespMenuVo {

    private Long id;

    private Long parentId;

    private String name;

    private List<RespMenuVo> children;
}
