package com.lalaalal.coffee.misc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.model.Drink;
import com.lalaalal.coffee.model.OrderItem;
import com.lalaalal.coffee.model.Temperature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderItemSerializerTest {
    @BeforeEach
    void setUp() {
        CoffeeApplication.initialize();
    }

    @Test
    void test() throws JsonProcessingException {
        OrderItem orderItem = new OrderItem("americano");
        orderItem.setArgument("count", Integer.class, 5);
        orderItem.setArgument("hasTumbler", Boolean.class, false);
        orderItem.setArgument(Drink.ARG_DECAFFEINATED, Boolean.class, false);
        orderItem.setArgument(Drink.ARG_TEMPERATURE, Temperature.class, Temperature.HOT);
        orderItem.setArgument("shot", Integer.class, 1);
        String serialized = CoffeeApplication.MAPPER.writeValueAsString(orderItem);
        System.out.println(serialized);

        OrderItem deserialized = CoffeeApplication.MAPPER.readValue(serialized, OrderItem.class);
        System.out.println(deserialized.calculateCost());
        System.out.println(deserialized.canMake());
        System.out.println(deserialized);
    }
}