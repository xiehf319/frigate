package cn.cici.frigate.msg.endpoint;

import cn.cici.frigate.component.context.SpringContextUtils;
import cn.cici.frigate.component.vo.R;
import cn.cici.frigate.msg.pojo.Greeting;
import cn.cici.frigate.msg.pojo.HelloMessage;
import cn.cici.frigate.msg.pojo.User;
import cn.cici.frigate.msg.service.RedisService;
import cn.cici.frigate.msg.websocket.WsConstants;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/11 18:00
 * @author: Heyfan Xie
 */
@Controller
@RequestMapping("/wsTemplate")
@Slf4j
public class MessageEndpoint {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;

    @Autowired
    private RedisService redisService;


    @PostMapping("/greeting")
    @ResponseBody
    public String greeting(@RequestBody Greeting greeting) {
        this.simpMessagingTemplate.convertAndSend(
                "/topic/greeting",
                new HelloMessage("Hello, " + greeting.getName() + "!"));
        return "ok";
    }

    @PostMapping("/sendToUser")
    @ResponseBody
    public String chat(HttpServletRequest request) {

        // 接收者
        String receiver = request.getParameter("receiver");

        // 消息内容
        String msg = request.getParameter("msg");

        HttpSession session = SpringContextUtils.getSession();
        User user = (User) session.getAttribute(WsConstants.SESSION_USER);

        HelloMessage helloMessage = new HelloMessage(
                MessageFormat.format("{0} say: {1}", user.getUsername(), msg));

        this.sendToUser(user.getUsername(), receiver, "/topic/reply", JSON.toJSONString(helloMessage));

        return "ok";
    }

    private void sendToUser(String sender, String receiver, String destination, String payload) {
        SimpUser simpUser = userRegistry.getUser(receiver);

        // 如果接收者存在
        if (simpUser != null && !StringUtils.isEmpty(simpUser.getName())) {
            this.simpMessagingTemplate.convertAndSendToUser(receiver, destination, payload);
        } else {
            // 否则将消息存在redis，等用户上线主动拉取未读消息
            String listKey = WsConstants.REDIS_UNREAD_MSG_PREFIX + receiver + ":" + destination;

            log.info(MessageFormat.format("消息接收者{0}还未建立连接, " +
                    "{1}发送的消息【{2}】将呗存储到Redis的【{3}】的列表中", receiver, sender, payload, destination));

            redisService.addToListRight(listKey, 30, TimeUnit.DAYS, payload);
        }
    }

    @PostMapping("/pullUnReadMessage")
    @ResponseBody
    public R<List<Object>> pullUnReadMessage(String destination) {
        HttpSession session = SpringContextUtils.getSession();
        User user = (User) session.getAttribute(WsConstants.SESSION_USER);
        String listKey = WsConstants.REDIS_UNREAD_MSG_PREFIX + user.getUsername() + ":" + destination;
        List<Object> messageList = redisService.rangeList(listKey, 0, -1);
        if (messageList != null && messageList.size() > 0) {
            redisService.delete(listKey);
        }
        return R.success(messageList);
    }
}
