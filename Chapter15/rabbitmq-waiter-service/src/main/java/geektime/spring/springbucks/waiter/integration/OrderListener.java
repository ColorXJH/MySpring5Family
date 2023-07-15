package geektime.spring.springbucks.waiter.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {
    @StreamListener(Barista.FINISHED_ORDERS)//这个payload也是一个long id 和发送端对应
    public void listenFinishedOrders(Long id) {
        log.info("We've finished an order [{}].", id);
    }
}
