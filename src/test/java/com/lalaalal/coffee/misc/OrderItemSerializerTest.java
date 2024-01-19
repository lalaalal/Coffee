package com.lalaalal.coffee.misc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.model.menu.Drink;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.model.menu.Temperature;
import com.lalaalal.coffee.model.order.OrderItem;
import com.lalaalal.coffee.model.order.argument.ArgumentWriter;
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
        ArgumentWriter argumentWriter = orderItem.getArguments();
        argumentWriter.setArgument(Menu.ARG_COUNT, Integer.class, 5);
        argumentWriter.setArgument(Drink.ARG_TUMBLER_COUNT, Integer.class, 1);
        argumentWriter.setArgument(Drink.ARG_DECAFFEINATED, Boolean.class, false);
        argumentWriter.setArgument(Drink.ARG_TEMPERATURE, Temperature.class, Temperature.HOT);
        argumentWriter.setArgument(Drink.ARG_SHOT, Integer.class, 1);
        String serialized = CoffeeApplication.MAPPER.writeValueAsString(orderItem);
        System.out.println(serialized);

        OrderItem deserialized = CoffeeApplication.MAPPER.readValue(serialized, OrderItem.class);
        System.out.println(deserialized.calculateCost());
        System.out.println(deserialized.canMake());
        System.out.println(deserialized);
    }
}