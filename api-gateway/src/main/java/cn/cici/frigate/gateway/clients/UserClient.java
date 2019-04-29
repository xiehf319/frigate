package cn.cici.frigate.gateway.clients;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description:
 * @createDate:2019/4/29$16:58$
 * @author: Heyfan Xie
 */
@FeignClient("account-server")
public interface UserClient {


}
