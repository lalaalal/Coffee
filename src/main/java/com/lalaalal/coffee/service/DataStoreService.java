package com.lalaalal.coffee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lalaalal.coffee.CoffeeApplication;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class DataStoreService<K, V> {
    protected static final ObjectMapper MAPPER = CoffeeApplication.MAPPER;
    protected final HashMap<K, V> data;
    protected final String saveFilePath;

    protected DataStoreService(Class<K> keyType, Class<V> valueType, Supplier<HashMap<K, V>> supplier, String saveFilePath) {
        this.data = supplier.get();
        this.saveFilePath = saveFilePath;

        try (InputStream inputStream = new FileInputStream(saveFilePath)) {
            TypeFactory typeFactory = MAPPER.getTypeFactory();
            Map<K, V> read = MAPPER.readValue(inputStream, typeFactory.constructMapType(Map.class, keyType, valueType));
            data.putAll(read);
        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            // TODO: 12/13/23 handle exception
            throw new RuntimeException(e);
        }
    }

    public boolean isValidKey(K key) {
        return data.containsKey(key);
    }

    public void save() {
        // TODO: 12/28/23 handle exception when directory does not exist
        try (FileOutputStream outputStream = new FileOutputStream(saveFilePath)) {
            MAPPER.writeValue(outputStream, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
