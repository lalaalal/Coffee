package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.model.menu.Drink;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.model.menu.Temperature;
import com.lalaalal.coffee.model.order.ArgumentCreator;
import com.lalaalal.coffee.model.order.ArgumentMapper;
import com.lalaalal.coffee.model.order.OrderArgument;

public class OrderArgumentCreatorRegistry extends Registry<ArgumentCreator> {
    public static ArgumentCreator COUNT;
    public static ArgumentCreator TUMBLER_COUNT;
    public static ArgumentCreator DECAFFEINATE;
    public static ArgumentCreator SHOT;
    public static ArgumentCreator TEMPERATURE;

    @Override
    public void initialize() {
        COUNT = register(Menu.ARG_COUNT, Integer.class, 1, ArgumentMapper.INTEGER_MAPPER);
        TUMBLER_COUNT = register(Drink.ARG_TUMBLER_COUNT, Integer.class, 0, ArgumentMapper.INTEGER_MAPPER);
        DECAFFEINATE = register(Drink.ARG_DECAFFEINATED, Boolean.class, false, ArgumentMapper.BOOLEAN_MAPPER);
        SHOT = register(Drink.ARG_SHOT, Integer.class, 0, ArgumentMapper.INTEGER_MAPPER);
        TEMPERATURE = register(Drink.ARG_TEMPERATURE, Temperature.class, Temperature.ICE, ArgumentMapper.TEMPERATURE_MAPPER);
    }

    public <T> ArgumentCreator register(String name, Class<T> type, T defaultValue, ArgumentMapper<T> mapper) {
        ArgumentCreator creator = () -> new OrderArgument<>(type, name, defaultValue, mapper);
        register(name, creator);
        return creator;
    }
}
