package cn.cici.frigate.tx;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: 类介绍：
 *      tx-lcn  tx-manager 服务
 * @createDate: 2019/7/16 17:10
 * @author: Heyfan Xie
 */
@SpringBootApplication
@EnableTransactionManagerServer
@EnableDiscoveryClient
public class TransactionManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionManagerApplication.class, args);
    }
}
