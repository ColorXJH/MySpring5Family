package geektime.spring.springbucks.waiter.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@ConfigurationProperties("order")
@RefreshScope
@Data
@Component
//问：Zookeeper和Consul作为配置中心的两个例子中，OrderProperties.java中，如果不加@RefreshScope注解，配置参数值也能自动更新的，
//这是由于Zookeeper和Consul的更新机制导致的吗？
//答：
//ZK和Consul的通知机制带来的好处是我们不需要去访问/refresh，在感知到节点值发生变更后，能自动刷新。这里只是自动触发了Refresh而已。
//至于不加@RefreshScope也能生效，是因为这个注解只是把这个Bean放进RefreshScope，
//加了@ConfigurationProperties的Bean也在RefreshScope里，所以能更新。
//可以试试把这两个注解都去掉，值用@Value注入，再看看就知道了。这是个好问题，我也做了些试验翻了些代码才来回答的。
public class OrderProperties {
    private Integer discount = 100;
    private String waiterPrefix = "springbucks-";
}
