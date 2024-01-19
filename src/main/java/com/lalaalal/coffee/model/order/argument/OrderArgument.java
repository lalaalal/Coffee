package com.lalaalal.coffee.model.order.argument;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

import java.io.IOException;

public class OrderArgument<T> {
    private final Class<T> type;
    @Getter
    private final String name;
    private T value;
    private final ArgumentMapper<T> mapper;

    public static <E> E getValue(Class<E> type, OrderArgument<?> argument) {
        return argument.getValue(type);
    }

    public OrderArgument(Class<T> type, String name, T defaultValue, ArgumentMapper<T> mapper) {
        this.type = type;
        this.name = name;
        value = defaultValue;
        this.mapper = mapper;
    }

    public void setValue(Object value, Class<?> type) {
        if (!type.equals(this.type))
            // TODO: 12/8/23 handle exception
            throw new RuntimeException("");

        this.value = this.type.cast(value);
    }

    public <E> E getValue(Class<E> type) {
        if (type.equals(this.type))
            return type.cast(this.value);
        // TODO: 12/6/23 handle exception
        throw new RuntimeException();
    }

    public void serializeArgument(JsonGenerator generator) throws IOException {
        mapper.serialize(generator, this);
    }

    public void deserializeArgument(JsonNode argumentsNode) {
        mapper.deserialize(argumentsNode, this);
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrderArgument<?> argument))
            return false;
        return argument.type.equals(this.type) && argument.value.equals(this.value);
    }
}
