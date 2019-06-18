package cn.cici.frigate.mqtt.push.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @createDate:2019/6/18$11:16$
 * @author: Heyfan Xie
 */
@Data
@Slf4j
@Builder
public class PushPayload {

    /**
     * 推送类型
     */
    private String type;

    /**
     * 推送对象
     */
    private String mobile;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 数量
     */
    private Integer badge = 1;

    /**
     * 铃声
     */
    private String sound = "default";

    public PushPayload(String type, String mobile, String title, String content, Integer badge, String sound) {
        this.type = type;
        this.mobile = mobile;
        this.title = title;
        this.content = content;
        this.badge = badge;
        this.sound = sound;
    }

}
