package geektime.spring.springbucks.waiter.integration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Barista {
    String NEW_ORDERS = "newOrders";
    String FINISHED_ORDERS = "finishedOrders";

    //监听订阅的消息，value指定名称，不写默认是方法名，上方为定义常量，也可以在注解中将这些常量放入L@Input(value=FINISHED_ORDERS), @Output(value=NEW_ORDERS)

    @Input
    SubscribableChannel finishedOrders();

    //要发送的消息

    @Output
    MessageChannel newOrders();
}
