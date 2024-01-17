package com.lalaalal.coffee.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lalaalal.coffee.CoffeeApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Store single object by key.
 *
 * @param <T> Object type to store, should be serializable with {@link ObjectMapper}
 * @author lalaalal
 */
public abstract class Registry<T> {
    protected static final ObjectMapper MAPPER = CoffeeApplication.MAPPER;

    protected final HashMap<String, T> registry = new HashMap<>();

    protected static <E> void loadListFromJson(String filePath, Class<E> type, Consumer<E> registerer) {
        if (!Files.exists(Path.of(filePath)))
            return;
        try (InputStream inputStream = new FileInputStream(filePath)) {
            TypeFactory typeFactory = MAPPER.getTypeFactory();
            List<E> list = MAPPER.readValue(inputStream, typeFactory.constructCollectionType(List.class, type));
            for (E element : list)
                registerer.accept(element);
        } catch (IOException e) {
            // TODO: 12/3/23 handle exception
        }
    }

    public abstract void initialize();

    public T get(String key) {
        return registry.get(key);
    }

    public Set<String> keys() {
        return registry.keySet();
    }

    public Collection<T> values() {
        return registry.values();
    }

    public String findKey(T value) {
        for (String key : registry.keySet()) {
            if (get(key).equals(value))
                return key;
        }
        return "null";
    }

    public void register(String key, T value) {
        if (registry.containsKey(key))
            throw new RuntimeException("Key (%s) already exists".formatted(key));
        registry.put(key, value);
    }

    public void alias(String original, String alias) {
        if (!registry.containsKey(original))
            throw new RuntimeException(original + " is not exists.");
        T value = get(original);
        register(alias, value);
    }
}
