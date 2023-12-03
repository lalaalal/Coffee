package com.lalaalal.coffee.registry;

import java.util.HashMap;
import java.util.function.Supplier;

public class Registries {
    private static final HashMap<Class<?>, Registry<?>> registries = new HashMap<>();

    public static final DrinkRegistry DRINK_REGISTRY = register(DrinkRegistry.class, DrinkRegistry::new);

    public static <T> T register(Class<T> type, Supplier<Registry<?>> supplier) {
        Registry<?> registry = supplier.get();
        registries.put(type, registry);

        return type.cast(registry);
    }

    public static void initialize() {
        for (Registry<?> registry : registries.values())
            registry.initialize();
    }

    public static <T> T get(Class<T> type) {
        return type.cast(registries.get(type));
    }
}
