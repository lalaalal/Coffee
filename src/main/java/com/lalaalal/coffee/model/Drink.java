package com.lalaalal.coffee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Drink {

    @Getter
    private final String id;

    @Getter
    private final int cost;

    private final TypeChecker typeChecker;

    public boolean canMake(Type type) {
        return typeChecker.canMake(type);
    }

    public Type[] getAvailableTypes() {
        return typeChecker.getAvailableTypes();
    }

    public String getTypeCheckerId() {
        return typeChecker.name();
    }
}
