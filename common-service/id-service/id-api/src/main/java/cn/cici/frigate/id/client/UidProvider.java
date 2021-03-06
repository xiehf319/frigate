package cn.cici.frigate.id.client;


import cn.cici.frigate.exception.BusinessException;
import cn.cici.frigate.exception.CommonResponseEnum;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @createDate:2019/7/9$10:45$
 * @author: Heyfan Xie
 */
public class UidProvider {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error")
    public Long getId() {
        return restTemplate.getForObject("http://ID-SERVICE/uid", Long.class);
    }

    public Long error() {
        throw new BusinessException(CommonResponseEnum.SERVER_ERROR, "ID SERVICE ERROR.");
    }
}
