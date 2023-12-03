package com.lalaalal.coffee.model;


import lombok.Getter;

import java.util.function.Function;

public enum TypeChecker {
    BOTH(type -> true, Type.HOT, Type.ICE),
    ICE_ONLY(type -> type == Type.ICE, Type.ICE),
    HOT_ONLY(type -> type == Type.HOT, Type.HOT);

    public static TypeChecker get(String name) {
        return switch (name) {
            case "BOTH" -> BOTH;
            case "ICE_ONLY" -> ICE_ONLY;
            case "HOT_ONLY" -> HOT_ONLY;
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }

    @Getter
    private final Type[] availableTypes;
    private final Function<Type, Boolean> checker;
    TypeChecker(Function<Type, Boolean> checker, Type... types) {
        this.checker = checker;
        this.availableTypes = types;
    }

    public boolean canMake(Type type) {
        return checker.apply(type);
    }
}