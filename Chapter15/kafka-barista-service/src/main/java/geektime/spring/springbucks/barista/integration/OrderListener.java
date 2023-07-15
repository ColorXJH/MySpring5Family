package geektime.spring.springbucks.barista.integration;

import geektime.spring.springbucks.barista.model.CoffeeOrder;
import geektime.spring.springbucks.barista.model.OrderState;
import geektime.spring.springbucks.barista.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
public class OrderListener {
    @Autowired
    private CoffeeOrderRepository orderRepository;
    @Autowired
    @Qualifier(Waiter.FINISHED_ORDERS)
    private MessageChannel finishedOrdersMessageChannel;
    @Value("${order.barista-prefix}${random.uuid}")
    private String barista;

    @StreamListener(Waiter.NEW_ORDERS)//监听消息
    @SendTo(Waiter.FINISHED_ORDERS)//使用注解，将方法的返回值发送到FINISHED_ORDERS，通过以上两个注解，将消息的payload放入了方法参数，同时将方法的返回结果有发送了出去
    public Long processNewOrder(Long id) {
        CoffeeOrder o = orderRepository.getOne(id);
        if (o == null) {
            log.warn("Order id {} is NOT valid.", id);
            throw new IllegalArgumentException("Order ID is INVAILD!");
        }
        log.info("Receive a new Order {}. Waiter: {}. Customer: {}",
                id, o.getWaiter(), o.getCustomer());
        o.setState(OrderState.BREWED);
        o.setBarista(barista);
        orderRepository.save(o);
        log.info("Order {} is READY.", id);
        return id;
    }
}
