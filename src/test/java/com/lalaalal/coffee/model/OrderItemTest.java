package com.lalaalal.coffee.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.registry.Registries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderItemTest {
    @BeforeEach
    void setUp() {
        CoffeeApplication.initialize();
    }

    @Test
    void test() throws JsonProcessingException {
        ObjectMapper mapper = CoffeeApplication.MAPPER;
        OrderItem item = new OrderItem();
        Drink drink = Registries.DRINK_REGISTRY.get("americano");
        item.setDrink(drink);
        item.setType(Type.HOT);
        item.setShot(0);
        item.setDecaffeinate(false);
        item.setHasTumbler(false);

        String str = mapper.writeValueAsString(item);
        System.out.println(str);
    }
}