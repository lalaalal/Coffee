package com.lalaalal.coffee.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lalaalal.coffee.CoffeeApplication;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataTable<K, V> implements DataTableReader<K, V> {
    protected static final ObjectMapper MAPPER = CoffeeApplication.MAPPER;
    private final Map<K, V> table;
    private final KeyGenerator<K> keyGenerator;
    private final String saveFilePath;

    public DataTable(Class<K> keyType, Class<V> valueType, KeyGenerator<K> keyGenerator, String saveFilePath) {
        this.saveFilePath = saveFilePath;
        this.table = new HashMap<>();
        this.keyGenerator = keyGenerator;
        this.keyGenerator.setKeySetSupplier(table::keySet);

        try (InputStream inputStream = new FileInputStream(saveFilePath)) {
            TypeFactory typeFactory = MAPPER.getTypeFactory();
            Map<K, V> read = MAPPER.readValue(inputStream, typeFactory.constructMapType(Map.class, keyType, valueType));
            table.putAll(read);
        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            // TODO: 12/13/23 handle exception
            throw new RuntimeException(e);
        }
    }

    public void save() {
        try (OutputStream outputStream = new FileOutputStream(saveFilePath)) {
            MAPPER.writeValue(outputStream, table);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public K add(V data) {
        K key = keyGenerator.generateKey();
        table.put(key, data);

        return key;
    }

    public void add(K key, V data) {
        table.put(key, data);
    }

    public void remove(K key) {
        table.remove(key);
    }

    @Override
    public V findFirst(Predicate<V> predicate) {
        for (V data : table.values()) {
            if (predicate.test(data))
                return data;
        }

        return null;
    }

    @Override
    public V get(K key) {
        return table.get(key);
    }

    @Override
    public Set<K> filterKey(Predicate<K> predicate) {
        return table.keySet().stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    @Override
    public List<V> filter(Predicate<V> predicate) {
        return table.values()
                .stream()
                .filter(predicate)
                .toList();
    }

    @Override
    public String toJsonString() {
        try {
            return MAPPER.writeValueAsString(table);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
