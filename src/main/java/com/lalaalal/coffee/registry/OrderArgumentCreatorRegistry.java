package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.model.menu.Drink;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.model.menu.Temperature;
import com.lalaalal.coffee.model.order.ArgumentCreator;
import com.lalaalal.coffee.model.order.OrderArgument;

public class OrderArgumentCreatorRegistry extends Registry<ArgumentCreator> {
    public static ArgumentCreator COUNT;
    public static ArgumentCreator TUMBLER_COUNT;
    public static ArgumentCreator DECAFFEINATE;
    public static ArgumentCreator SHOT;
    public static ArgumentCreator TEMPERATURE;

    @Override
    public void initialize() {
        COUNT = register(Menu.ARG_COUNT, Integer.class, 1);
        TUMBLER_COUNT = register(Drink.ARG_TUMBLER_COUNT, Integer.class, 0);
        DECAFFEINATE = register(Drink.ARG_DECAFFEINATED, Boolean.class, false);
        SHOT = register(Drink.ARG_SHOT, Integer.class, 0);
        TEMPERATURE = register(Drink.ARG_TEMPERATURE, Temperature.class, Temperature.ICE);
    }

    public <T> ArgumentCreator register(String name, Class<T> type, T defaultValue) {
        ArgumentCreator creator = () -> new OrderArgument<>(type, name, defaultValue);
        register(name, creator);
        return creator;
    }
}
