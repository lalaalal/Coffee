package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lalaalal.coffee.CoffeeApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderTest {

    @Test
    void test() throws JsonProcessingException {
        Order order = new Order();
        OrderItem item = new OrderItem("americano");
        order.setId("test");
        order.add(item);
        String s = CoffeeApplication.MAPPER.writeValueAsString(order);
        System.out.println(s);
        System.out.println(CoffeeApplication.MAPPER.readValue(s, Order.class));
    }
}