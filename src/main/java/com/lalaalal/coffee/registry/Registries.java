package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.exception.FatalError;
import com.lalaalal.coffee.initializer.Initialize.Time;
import com.lalaalal.coffee.initializer.Initializer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

public class Registries implements Initializer {
    private static final HashMap<Class<?>, Registry<?>> registries = new HashMap<>();
    private static final Queue<Registry<?>> initializeQueue = new LinkedList<>();

    private Registries() {
    }

    public static <T> T register(Class<T> type, Supplier<Registry<?>> supplier) {
        Registry<?> registry = supplier.get();
        registries.put(type, registry);
        initializeQueue.add(registry);

        return type.cast(registry);
    }

    public static void initialize() throws FatalError {
        Initializer.initialize(Registries.class, Time.Pre);
        while (!initializeQueue.isEmpty()) {
            Registry<?> registry = initializeQueue.poll();
            registry.initialize();
        }
        Initializer.initialize(Registries.class, Time.Post);
    }

    public static <T> T get(Class<T> type) {
        return type.cast(registries.get(type));
    }

    public static <T> T get(Class<? extends Registry<T>> type, String key) {
        return get(type).get(key);
    }
}
