package geektime.spring.springbucks.customer.support;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@ConfigurationProperties("waiter")
@EnableConfigurationProperties(FixedDiscoveryClient.class)//是将让使用了 @ConfigurationProperties 注解的配置类生效,将该类注入到 IOC 容器中,
//其实不配置也可以，以为后面在启动类里面直接生成了一个bean,所以说在这里配置@Component和在后面配置@Bean的效果是一样的
@Setter
public class FixedDiscoveryClient implements DiscoveryClient {
    public static final String SERVICE_ID = "waiter-service";
    // waiter.services
    private List<String> services;

    @Override
    public String description() {
        return "DiscoveryClient that uses service.list from application.yml.";
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        if (!SERVICE_ID.equalsIgnoreCase(serviceId)) {
            return Collections.emptyList();
        }
        // 这里忽略了很多边界条件判断，认为就是 HOST:PORT 形式
        return services.stream()
                .map(s -> new DefaultServiceInstance(s,
                        SERVICE_ID,
                        s.split(":")[0],
                        Integer.parseInt(s.split(":")[1]),
                        false)).collect(Collectors.toList());
    }

    @Override
    public List<String> getServices() {
        return Collections.singletonList(SERVICE_ID);
    }
}
