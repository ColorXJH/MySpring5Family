package geektime.spring.springbucks.customer;

import geektime.spring.springbucks.customer.support.CustomConnectionKeepAliveStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	//使用Feign的注解定义接口的方法，指定请求的URL、HTTP方法、请求参数等信息。
	//在Feign的配置类中，使用@Bean注解配置CloseableHttpClient，并设置连接池管理、连接保持活动等策略。
	//在服务类中注入Feign生成的接口，并使用它来发送HTTP请求。

	@Bean
	public CloseableHttpClient httpClient() {
		return HttpClients.custom()
				.setConnectionTimeToLive(30, TimeUnit.SECONDS)
				.evictIdleConnections(30, TimeUnit.SECONDS)
				.setMaxConnTotal(200)
				.setMaxConnPerRoute(20)
				.disableAutomaticRetries()
				.setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
				.build();
	}
}
