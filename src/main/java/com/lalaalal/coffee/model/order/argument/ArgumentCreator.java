package com.lalaalal.coffee.model.order.argument;

@FunctionalInterface
public interface ArgumentCreator {
    OrderArgument<?> create();
}
