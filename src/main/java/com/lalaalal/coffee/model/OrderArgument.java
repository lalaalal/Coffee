package com.lalaalal.coffee.model;

import lombok.Getter;

public class OrderArgument<T> {
    private final Class<T> type;
    @Getter
    private final String name;
    private T value;

    public OrderArgument(Class<T> type, String name) {
        this.type = type;
        this.name = name;
    }

    public void setValue(Object value, Class<?> type) {
        if (!type.equals(this.type))
            // TODO: 12/8/23 handle exception
            throw new RuntimeException("");

        this.value = this.type.cast(value);
    }

    public static <E> E get(Class<E> type, OrderArgument<?> argument) {
        if (type.equals(argument.type))
            return type.cast(argument.value);
        // TODO: 12/6/23 handle exception
        throw new RuntimeException("Temperature not matches.");
    }
}
