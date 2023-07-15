package geektime.spring.springbucks.waiter;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import geektime.spring.springbucks.waiter.controller.PerformanceInteceptor;
import geektime.spring.springbucks.waiter.integration.Barista;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
@EnableDiscoveryClient
@EnableBinding(Barista.class)
public class WaiterServiceApplication implements WebMvcConfigurer {
	//正常情况下安装并启动kafka:
	// docker pull confluentinc/cp-kafka
	// docker run -d --name kafka -p 9092:9092 --network=host -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 confluentinc/cp-kafka
	// 以上是一套企业级的kafka，惠子东帮我们下载zookeeper,
	//本项目我们自己使用了docker-compose工具来定制自己的kafka环境（因为我们本身已经有了zookeeper镜像，通过自己特定的文件配置容器之间的依赖关系，轻松地定义和管理复杂的容器化应用程序，而无需手动运行多个docker命令）
		//cmd进入项目的kafka-waiter-service目录执行docker-compose.yml文件,如下：
			//docker-compose up -d   没有镜像会帮你下载

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
