package cn.cici.frigate.sms.api;

import cn.cici.frigate.component.exception.BusinessException;
import cn.cici.frigate.component.exception.CommonResponseEnum;
import cn.cici.frigate.component.sms.SmsCodeInfo;
import cn.cici.frigate.component.vo.R;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/15 15:14
 * @author: Heyfan Xie
 */
@Component
public class SmsCodeClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送短信
     *
     * @param mobile mobile
     * @param code
     */
    @HystrixCommand(fallbackMethod = "error")
    public void send(String mobile, String code, Integer type) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        SmsCodeInfo smsCodeInfo = new SmsCodeInfo();
        smsCodeInfo.setMobile(mobile);
        smsCodeInfo.setCode(code);
        smsCodeInfo.setType(type);
        ResponseEntity<R> result = restTemplate.postForEntity("http://SMS-SERVICE/sms/code/send", new HttpEntity<>(smsCodeInfo, map), R.class);
        CommonResponseEnum.SERVER_ERROR.assertTrue(result.getStatusCode() == HttpStatus.OK);
    }


    /**
     * 校验
     *
     * @param mobile mobile
     * @param code
     */
    @HystrixCommand(fallbackMethod = "error")
    public void verify(String mobile, String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        SmsCodeInfo smCodeInfo = new SmsCodeInfo();
        smCodeInfo.setMobile(mobile);
        smCodeInfo.setCode(code);
        ResponseEntity<R> result = restTemplate.postForEntity("http://SMS-SERVICE/sms/code/verify", new HttpEntity<>(smCodeInfo, map), R.class);
        CommonResponseEnum.SERVER_ERROR.assertTrue(result.getStatusCode() == HttpStatus.OK);
    }


    public Long error() {
        throw new BusinessException(CommonResponseEnum.SERVER_ERROR, "MSG SERVICE ERROR.");
    }
}
