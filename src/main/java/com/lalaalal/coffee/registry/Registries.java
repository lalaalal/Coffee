
package com.lalaalal.coffee.registry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Supplier;

import com.lalaalal.coffee.initializer.Initializer;
import com.lalaalal.coffee.initializer.Initialize.Time;

public class Registries extends Initializer {
    private static final HashMap<Class<?>, Registry<?>> registries = new HashMap<>();
    private static final Queue<Registry<?>> initializeQueue = new LinkedList<>();
    public static <T> T register(Class<T> type, Supplier<Registry<?>> supplier) {
        Registry<?> registry = supplier.get();
        registries.put(type, registry);
        initializeQueue.add(registry);

        return type.cast(registry);
    }

    public static void initialize() {
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

    public static <T, E> T get(Class<? extends Registry<T>> type, String key) {
        return get(type).get(key);
    }

    private Registries() {
    }
}
