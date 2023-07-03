package geektime.spring.hello;

import geektime.spring.hello.greeting.GreetingApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
//默认扫描geektime.spring.hello及其所有子包下面的配置类，所以我们的手动配置的gektime-autoconfigure-backport项目下的配置类会被扫描到
//如果没有明确的上下级关系，则需要在这里显示的扫描需要注册到springboot上下文中的包
@SpringBootApplication
public class AutoconfigureDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoconfigureDemoApplication.class, args);
	}
	@Bean
	public GreetingApplicationRunner greetingApplicationRunner(){
		return new GreetingApplicationRunner("color-xjh-spring");
	}

}
