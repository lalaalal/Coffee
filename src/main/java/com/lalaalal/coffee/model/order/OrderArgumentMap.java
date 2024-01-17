package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.registry.OrderArgumentCreatorRegistry;
import com.lalaalal.coffee.registry.Registries;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class OrderArgumentMap implements ArgumentWriter {
    private final HashMap<String, OrderArgument<?>> arguments = new HashMap<>();

    public OrderArgumentMap(Menu menu) {
        for (ArgumentCreator argumentCreator : menu.getRequiredArgumentCreators()) {
            OrderArgument<?> argument = argumentCreator.create();
            arguments.put(argument.getName(), argument);
        }
    }

    public OrderArgumentMap(Set<String> argumentNames) {
        for (String argumentName : argumentNames) {
            ArgumentCreator creator = Registries.get(OrderArgumentCreatorRegistry.class, argumentName);
            OrderArgument<?> argument = creator.create();
            arguments.put(argument.getName(), argument);
        }
    }

    @Override
    public Set<String> getArgumentNames() {
        return arguments.keySet();
    }

    @Override
    public <T> T getArgumentValue(String name, Class<T> type) {
        if (!arguments.containsKey(name))
            // TODO: 12/8/23 handle exception
            throw new RuntimeException();
        return OrderArgument.get(type, arguments.get(name));
    }

    @Override
    public <T> void setArgument(String name, Class<T> type, T value) {
        if (!arguments.containsKey(name))
            // TODO: 12/8/23 handle exception
            throw new RuntimeException();
        OrderArgument<?> argument = arguments.get(name);
        argument.setValue(value, type);
    }

    public void serializeArguments(JsonGenerator generator) throws IOException {
        for (OrderArgument<?> argument : arguments.values())
            argument.serializeArgument(generator);
    }

    public void deserializeArguments(JsonNode argumentsNode) {
        for (OrderArgument<?> argument : arguments.values())
            argument.deserializeArgument(argumentsNode);
    }


    public boolean canBeCombinedWith(OrderArgumentMap other, Menu menu) {
        for (String targetArgumentName : menu.getCombinationCheckArguments()) {
            OrderArgument<?> a = this.arguments.get(targetArgumentName);
            OrderArgument<?> b = other.arguments.get(targetArgumentName);
            if (!a.equals(b))
                return false;
        }
        return true;
    }
}
