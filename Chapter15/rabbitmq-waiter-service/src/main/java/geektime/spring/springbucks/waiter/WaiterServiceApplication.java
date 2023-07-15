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
	//docker pull rabbitmq 或者带管理界面： docker pull rabbitmq:3.7-management
	//运行镜像： docker run --name rabbitmq -d -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=spring -e RABBITMQ_DEFAULT_PASS=spring rabbitmq:3.7-management
	//以上两个端口一个是服务端口，一个是管理界面端口

	//docker 安装运行mysql
	//docker pull mysql
		//因为本地计算机有mysql服务，所以本地的3306端口被占用了额，我们将docker-mysql的3306映射到本机的3360端口
	//docker run -p 3360:3306 --name mysql --privileged=true -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest
		//docker exec -it mysql bash
		//mysql -uroot -p123456
		//create database mybatis;   创建数据库实例
		//创建用户名密码 CREATE USER 'ColorXJH'@'%' IDENTIFIED BY '2012WananXJH';    这里@之后的%表示可以从任何主机登录mysql,如果替换为特定的ip，则只能该ip用户可以访问数据库
		//赋予权限：GRANT privileges ON database.table TO 'username'@'host';
			//例如：GRANT SELECT, INSERT ,DELETE,CREATE  ON mybatis.* TO 'ColorXJH'@'%';
			//GRANT ALL PRIVILEGES ON *.* TO 'ColorXJH'@'%'; 赋予所有权限
		//撤销权限：REVOKE privileges ON database.table FROM 'username'@'host';
			//REVOKE ALL PRIVILEGES ON mydb.* FROM 'newuser'@'%';
		//修改用户密码：SET PASSWORD FOR 'username'@'host' = 'newpassword';
		//查看用户权限：SHOW GRANTS FOR 'username'@'host';
			//SHOW GRANTS FOR 'newuser'@'%'; 要查看名为 newuser 的用户在所有数据库中所有表的所有权限
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
