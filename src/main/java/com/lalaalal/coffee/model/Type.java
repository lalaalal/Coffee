package com.lalaalal.coffee.model;

public enum Type {
    HOT, ICE;

    public static Type get(String name) {
        return switch (name) {
            case "HOT" -> HOT;
            case "ICE" -> ICE;
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }
}