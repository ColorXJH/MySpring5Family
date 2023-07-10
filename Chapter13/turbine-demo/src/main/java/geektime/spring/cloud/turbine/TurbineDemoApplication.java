package geektime.spring.cloud.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class TurbineDemoApplication {
	//聚合地址：http://localhost:8090/actuator/hystrix.stream=customer-service 将其放入hystrix监控也可以查看图形化聚合监控
	public static void main(String[] args) {
		SpringApplication.run(TurbineDemoApplication.class, args);
	}

}
