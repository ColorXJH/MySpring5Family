package geektime.spring.springbucks.customer.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import geektime.spring.springbucks.customer.integration.CoffeeOrderService;
import geektime.spring.springbucks.customer.integration.CoffeeService;
import geektime.spring.springbucks.customer.model.Coffee;
import geektime.spring.springbucks.customer.model.CoffeeOrder;
import geektime.spring.springbucks.customer.model.NewOrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @GetMapping("/menu")
    public List<Coffee> readMenu() {
        List<Coffee> list = coffeeService.getAll();
        log.info("Read Menu: {} coffee", list.size());
        return list;
    }

    @PostMapping("/order")
    @HystrixCommand(fallbackMethod = "fallbackCreateOrder")
    public CoffeeOrder createOrder() {
        NewOrderRequest orderRequest = NewOrderRequest.builder()
                .customer("Li Lei")
                .items(Arrays.asList("capuccino"))
                .build();
        CoffeeOrder order = coffeeOrderService.create(orderRequest);
        log.info("Order ID: {}", order != null ? order.getId() : "-");
        return order;
    }
    //返回值、参数列表 一致
    public CoffeeOrder fallbackCreateOrder() {
        log.warn("Fallback to NULL order.");
        return null;
    }
}


/*
* 在使用Hystrix进行服务容错时，fallbackMethod和fallbackFactory都可以用来定义当服务调用失败或超时时的备用逻辑。它们的区别在于：
* fallbackMethod：fallbackMethod是在同一个类中定义的备用方法。当服务调用失败时，Hystrix会调用fallbackMethod所指定的方法来返回备用结果。fallbackMethod需要满足以下条件：
方法签名与原始方法一致，包括参数列表和返回类型。
不能有任何抛出异常。
@HystrixCommand(fallbackMethod = "fallbackMethod")
public String originalMethod() {
    // 原始方法的逻辑
}

public String fallbackMethod() {
    // 备用方法的逻辑
}

* fallbackFactory：fallbackFactory是一个工厂类，用于创建fallback实例。每次服务调用失败时，Hystrix都会调用fallbackFactory创建一个新的fallback实例，并调用该实例的方法来返回备用结果。fallbackFactory需要实现FallbackFactory接口，并实现create方法。fallbackFactory可以用于实现更复杂的逻辑，例如根据不同的异常类型返回不同的备用结果。

例如：
* @HystrixCommand(fallbackFactory = MyFallbackFactory.class)
public String originalMethod() {
    // 原始方法的逻辑
}

public class MyFallbackFactory implements FallbackFactory<MyFallback> {
    @Override
    public MyFallback create(Throwable cause) {
        return new MyFallback(cause);
    }
}

public class MyFallback {
    private Throwable cause;

    public MyFallback(Throwable cause) {
        this.cause = cause;
    }

    public String fallbackMethod() {
        // 根据异常类型返回不同的备用结果
    }
}

* 总结来说，fallbackMethod适用于简单的备用逻辑，而fallbackFactory适用于更复杂的备用逻辑，并且可以根据异常类型进行处理。
*/