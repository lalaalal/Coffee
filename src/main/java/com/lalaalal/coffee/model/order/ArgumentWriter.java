package com.lalaalal.coffee.model.order;

import java.util.function.BiFunction;

public interface ArgumentWriter extends ArgumentReader {
    <T> void setArgument(String name, Class<T> type, T value);

    default <T> void combineValue(String argumentName, Class<T> type, ArgumentReader other, BiFunction<T, T, T> operator) {
        T a = this.getArgumentValue(argumentName, type);
        T b = other.getArgumentValue(argumentName, type);
        T combinedValue = operator.apply(a, b);
        this.setArgument(argumentName, type, combinedValue);
    }
}
