package cn.cici.frigate.mail.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/16 11:38
 * @author: Heyfan Xie
 */
@Component
public class MailSenderClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送邮件
     */
    public void sendEmail() {

    }
}
