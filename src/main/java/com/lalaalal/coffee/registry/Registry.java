package com.lalaalal.coffee.registry;

import java.util.Collection;
import java.util.HashMap;

public abstract class Registry<T> {
    private final HashMap<String, T> registry = new HashMap<>();

    public abstract void initialize();

    public T get(String key) {
        return registry.get(key);
    }

    public Collection<T> values() {
        return registry.values();
    }

    public void register(String key, T value) {
        if (registry.containsKey(key))
            throw new RuntimeException("Key (%s) already exists".formatted(key));
        registry.put(key, value);
    }
}
