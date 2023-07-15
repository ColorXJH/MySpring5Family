package geektime.spring.springbucks.barista;

import geektime.spring.springbucks.barista.integration.Waiter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
@EnableBinding(Waiter.class)
public class BaristaServiceApplication {
	//让zipkin通过rabbitmq接受消息：
		//运行zipkin镜像：docker run --name rabbit-zipkin -d -p 9411:9411 --link rabbitmq -e RABBIT_ADDRESS=rabbitmq:5672 -e RABBIT_USER=spring -e RABBIT_PASSWORD=spring openzipkin/zipkin

	public static void main(String[] args) {
		SpringApplication.run(BaristaServiceApplication.class, args);
	}

}
