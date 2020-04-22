package cn.cici.frigate.rbac.controller.vo.resp;

import lombok.Data;

import java.util.List;

/**
 * 类描述:
 *
 * @author 003300
 * @version 1.0
 * @date 2020/4/22 17:55
 */
@Data
public class RespMenuVo {

    private Long id;

    private Long parentId;

    private String name;

    private List<RespMenuVo> children;
}
