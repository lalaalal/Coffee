package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.lalaalal.coffee.model.menu.Temperature;

import java.io.IOException;

public interface ArgumentMapper<T> {
    ArgumentMapper<Integer> INTEGER_MAPPER = new ArgumentMapper<>() {
        @Override
        public void serialize(JsonGenerator generator, OrderArgument<Integer> argument) throws IOException {
            String name = argument.getName();
            int value = OrderArgument.get(Integer.class, argument);
            generator.writeNumberField(name, value);
        }

        @Override
        public void deserialize(JsonNode node, OrderArgument<Integer> argument) {
            int value = node.get(argument.getName()).asInt();
            argument.setValue(value, Integer.class);
        }
    };

    ArgumentMapper<Boolean> BOOLEAN_MAPPER = new ArgumentMapper<>() {
        @Override
        public void serialize(JsonGenerator generator, OrderArgument<Boolean> argument) throws IOException {
            String name = argument.getName();
            boolean value = OrderArgument.get(Boolean.class, argument);
            generator.writeBooleanField(name, value);
        }

        @Override
        public void deserialize(JsonNode node, OrderArgument<Boolean> argument) {
            boolean value = node.get(argument.getName()).asBoolean();
            argument.setValue(value, Boolean.class);
        }
    };

    ArgumentMapper<Temperature> TEMPERATURE_MAPPER = new ArgumentMapper<>() {
        @Override
        public void serialize(JsonGenerator generator, OrderArgument<Temperature> argument) throws IOException {
            String name = argument.getName();
            Temperature value = OrderArgument.get(Temperature.class, argument);
            generator.writeStringField(name, value.name());
        }

        @Override
        public void deserialize(JsonNode node, OrderArgument<Temperature> argument) {
            String textValue = node.get(argument.getName()).asText();
            Temperature value = Temperature.get(textValue);
            argument.setValue(value, Temperature.class);
        }
    };

    void serialize(JsonGenerator generator, OrderArgument<T> argument) throws IOException;

    void deserialize(JsonNode node, OrderArgument<T> argument);
}
