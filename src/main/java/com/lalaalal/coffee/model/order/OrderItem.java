package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.registry.MenuRegistry;
import com.lalaalal.coffee.registry.Registries;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class OrderItem implements ArgumentReader {
    private final Menu menu;
    private final HashMap<String, OrderArgument<?>> arguments = new HashMap<>();

    public OrderItem(String menu) {
        this.menu = Registries.get(MenuRegistry.class, menu);
        for (ArgumentCreator argumentCreator : this.menu.getRequiredArgumentCreators()) {
            OrderArgument<?> argument = argumentCreator.create();
            arguments.put(argument.getName(), argument);
        }
    }

    public String getMenuId() {
        return menu.getId();
    }

    @Override
    public Collection<String> getArgumentNames() {
        return arguments.keySet();
    }

    @Override
    public <T> T getArgumentValue(String name, Class<T> type) {
        if (!arguments.containsKey(name))
            // TODO: 12/8/23 handle exception
            throw new RuntimeException();
        return OrderArgument.get(type, arguments.get(name));
    }

    public <T> void setArgument(String name, Class<T> type, T value) {
        if (!arguments.containsKey(name))
            // TODO: 12/8/23 handle exception
            throw new RuntimeException();
        OrderArgument<?> argument = arguments.get(name);
        argument.setValue(value, type);
    }

    public void serializeArguments(JsonGenerator generator) throws IOException {
        menu.serializeArguments(generator, this);
    }

    public void deserializeArguments(JsonNode argumentsNode) {
        menu.deserializeArguments(argumentsNode, this);
    }

    public boolean canMake() {
        return menu.canMake(this);
    }

    public int calculateCost() {
        return menu.calculateCost(this);
    }
}
