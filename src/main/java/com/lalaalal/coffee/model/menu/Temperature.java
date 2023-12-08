package com.lalaalal.coffee.model.menu;

public enum Temperature {
    HOT, ICE;

    public static Temperature get(String name) {
        return switch (name) {
            case "HOT" -> HOT;
            case "ICE" -> ICE;
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }
}