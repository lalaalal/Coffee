package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.model.menu.Drink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderTest {
    @BeforeEach
    void setUp() {
        CoffeeApplication.initialize();
    }

    @Test
    void test() throws JsonProcessingException {
        Order order = new Order();
        OrderItem item = new OrderItem("americano");
        item.setArgument(Drink.ARG_COUNT, Integer.class, 2);
        order.setId("test");
        order.add(item);
        String s = CoffeeApplication.MAPPER.writeValueAsString(order);
        System.out.println(s);
        System.out.println(CoffeeApplication.MAPPER.readValue(s, Order.class));
    }
}