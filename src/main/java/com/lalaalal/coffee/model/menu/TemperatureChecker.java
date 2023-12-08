package com.lalaalal.coffee.model.menu;


import lombok.Getter;

import java.util.function.Function;

public enum TemperatureChecker {
    BOTH(temperature -> true, Temperature.HOT, Temperature.ICE),
    ICE_ONLY(temperature -> temperature == Temperature.ICE, Temperature.ICE),
    HOT_ONLY(temperature -> temperature == Temperature.HOT, Temperature.HOT);

    public static TemperatureChecker get(String name) {
        return switch (name) {
            case "BOTH" -> BOTH;
            case "ICE_ONLY" -> ICE_ONLY;
            case "HOT_ONLY" -> HOT_ONLY;
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }

    @Getter
    private final Temperature[] availableTemperatures;
    private final Function<Temperature, Boolean> checker;

    TemperatureChecker(Function<Temperature, Boolean> checker, Temperature... temperatures) {
        this.checker = checker;
        this.availableTemperatures = temperatures;
    }

    public boolean canMake(Temperature temperature) {
        return checker.apply(temperature);
    }
}