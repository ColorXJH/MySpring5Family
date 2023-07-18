package geektime.spring.springbucks.customer.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import geektime.spring.springbucks.customer.integration.CoffeeService;
import geektime.spring.springbucks.customer.model.Coffee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TestController
 * @Package: geektime.spring.springbucks.customer.web
 * @Description:
 * @Datetime: 2023/7/18 23:34
 * @author: ColorXJH
 */
@RestController
public class TestController {
    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/test")
    public Map<String,Object> Test(){
        Map<String,Object>result=new HashMap<>();
        Coffee coffee=new Coffee();
        coffee.setCreateTime(new Date());
        coffee.setId(1233443333L);
        coffee.setName("xjh");
        String jsonString=null;
        ObjectMapper mapper=new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        try {
            jsonString=mapper.writeValueAsString(coffee);
            result=coffeeService.testFeign(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
