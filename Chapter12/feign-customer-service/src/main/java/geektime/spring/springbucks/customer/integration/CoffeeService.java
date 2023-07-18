package geektime.spring.springbucks.customer.integration;

import geektime.spring.springbucks.customer.model.Coffee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "waiter-service", contextId = "coffee", path = "/coffee")
// 不要在接口上加@RequestMapping
public interface CoffeeService {
    @GetMapping(path = "/", params = "!name")
    List<Coffee> getAll();

    @GetMapping("/{id}")
    Coffee getById(@PathVariable Long id);

    @GetMapping(path = "/", params = "name")
    Coffee getByName(@RequestParam String name);

    @RequestMapping("/testFeign")
    Map<String,Object> testFeign(@RequestBody String msg);
}
