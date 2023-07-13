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
	// 问：配置的加载顺序，如果前面的配置跟后面的配置有一个相同的属性，比如说 都有一个order.discount，那么结果后面的配置文件 就会覆盖掉前面配置文件中的值吗？
	// 答：是按照优先级来的，优先级高的文件里的配置会最终生效，优先级是 应用名-profile.yml>应用名.yml>application-profile.yml>application.yml ，
	// 所以如果你的配置在应用名.yml里和application.yml里同时出现，那么应用名.yml里的会生效。
	// zk consul git+hook spring-cloud-bus+mq 推送机制
	public static void main(String[] args) {
		SpringApplication.run(WaiterServiceApplication.class, args);
	}

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
