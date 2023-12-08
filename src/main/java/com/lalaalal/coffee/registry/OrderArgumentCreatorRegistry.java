package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.model.menu.Drink;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.model.menu.Temperature;
import com.lalaalal.coffee.model.order.ArgumentCreator;
import com.lalaalal.coffee.model.order.OrderArgument;

public class OrderArgumentCreatorRegistry extends Registry<ArgumentCreator> {
    public static ArgumentCreator COUNT;
    public static ArgumentCreator HAS_TUMBLER;
    public static ArgumentCreator DECAFFEINATE;
    public static ArgumentCreator SHOT;
    public static ArgumentCreator TEMPERATURE;

    @Override
    public void initialize() {
        COUNT = register(Menu.ARG_COUNT, Integer.class);
        HAS_TUMBLER = register(Drink.ARG_HAS_TUMBLER, Boolean.class);
        DECAFFEINATE = register(Drink.ARG_DECAFFEINATED, Boolean.class);
        SHOT = register(Drink.ARG_SHOT, Integer.class);
        TEMPERATURE = register(Drink.ARG_TEMPERATURE, Temperature.class);
    }

    public ArgumentCreator register(String name, Class<?> type) {
        ArgumentCreator creator = () -> new OrderArgument<>(type, name);
        register(name, creator);
        return creator;
    }
}
