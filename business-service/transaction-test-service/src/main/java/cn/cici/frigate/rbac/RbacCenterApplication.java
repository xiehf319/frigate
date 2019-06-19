package cn.cici.frigate.rbac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RbacCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RbacCenterApplication.class, args);
	}

}
