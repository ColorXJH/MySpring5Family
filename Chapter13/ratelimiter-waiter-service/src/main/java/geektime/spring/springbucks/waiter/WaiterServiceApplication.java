package geektime.spring.springbucks.waiter;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import geektime.spring.springbucks.waiter.controller.PerformanceInteceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableDiscoveryClient
public class WaiterServiceApplication implements WebMvcConfigurer {
	//ab做post请求的时候需要创建一个psot内容体，
	//touch /temp/empty.txt
	//ab -c 5 -n 10 -p /temp/empty.txt http://localhost:8090/customer/order

	public static void main(String[] args) {
		SpringApplication.run(WaiterServiceApplication.class, args);
	}
	//断路器 隔仓 限流 重试这几个我在一个方法上都加了会以什么样的优先级顺序执行呢？
		// 默认顺序是 retry -> bulkhead -> circuitbreaker -> ratelimiter，
	    // 可以看RateLimiterConfigurationProperties.rateLimiterAspectOrder的值，其他几个也是类似的。这些切面都是实现了Ordered的，因此可以指定顺序执行

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PerformanceInteceptor())
				.addPathPatterns("/coffee/**").addPathPatterns("/order/**");
	}

	@Bean
	public Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonBuilderCustomizer() {
		return builder -> {
			builder.indentOutput(true);
			builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		};
	}
}
