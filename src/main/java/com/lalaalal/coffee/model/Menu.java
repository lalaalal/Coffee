package com.lalaalal.coffee.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.registry.OrderArgumentCreatorRegistry;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.List;

@Getter
@AllArgsConstructor
public class Menu {
    public static final String ARG_COUNT = "count";

    protected static int tumblerDiscount;

    private final String id;
    private final int cost;
    private final Group group;

    public static void initialize() {
        tumblerDiscount = Configurations.getIntConfiguration("tumbler.discount");
    }

    public List<ArgumentCreator> getRequiredArgumentCreators() {
        return List.of(OrderArgumentCreatorRegistry.COUNT);
    }

    public boolean canMake(ArgumentReader arguments) {
        return true;
    }

    public int calculateCost(ArgumentReader arguments) {
        int count = arguments.getArgumentValue(ARG_COUNT, Integer.class);
        return cost * count;
    }

    public void deserializeArguments(JsonNode argumentsNode, OrderItem orderItem) {
        int count = argumentsNode.get(ARG_COUNT).asInt();
        orderItem.setArgument(ARG_COUNT, Integer.class, count);
    }

    public final void serializeArguments(JsonGenerator generator, ArgumentReader arguments) throws IOException {
        generator.writeObjectFieldStart("arguments");
        this._serializeArguments(generator, arguments);
        generator.writeEndObject();
    }

    protected void _serializeArguments(JsonGenerator generator, ArgumentReader arguments) throws IOException {
        int count = arguments.getArgumentValue(ARG_COUNT, Integer.class);
        generator.writeNumberField(ARG_COUNT, count);

    }
}
