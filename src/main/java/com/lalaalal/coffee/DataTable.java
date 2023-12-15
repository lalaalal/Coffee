package com.lalaalal.coffee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataTable<K, V> {
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

    public void remove(K id) {
        table.remove(id);
    }

    public V findFirst(Predicate<V> predicate) {
        for (V data : table.values()) {
            if (predicate.test(data))
                return data;
        }

        return null;
    }

    public V findById(K id) {
        return table.get(id);
    }

    public Set<K> filterKey(Predicate<K> predicate) {
        return table.keySet().stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    public List<V> filter(Predicate<V> predicate) {
        return table.values()
                .stream()
                .filter(predicate)
                .toList();
    }

}
