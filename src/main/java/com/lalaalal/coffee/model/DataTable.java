package com.lalaalal.coffee.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lalaalal.coffee.CoffeeApplication;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

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
        // TODO: 12/28/23 handle exception when directory does not exist
        try (FileOutputStream outputStream = new FileOutputStream(saveFilePath)) {
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

    @Override
    public Collection<V> collect() {
        return table.values();
    }

    @Override
    public Stream<V> stream() {
        return table.values()
                .stream();
    }

    public boolean containsKey(K key) {
        return table.containsKey(key);
    }

    public void remove(K key) {
        table.remove(key);
    }

    @Override
    public V get(K key) {
        return table.get(key);
    }
}
