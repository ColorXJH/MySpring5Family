package geektime.spring.springbucks.waiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class WaiterServiceApplication {
	//oss:对象存储服务 Object Storage Service cdn:内容分发网络 Content Delivery Network  单点登录(SingleSignOn，SSO)
	public static void main(String[] args) {
		SpringApplication.run(WaiterServiceApplication.class, args);
	}

}
