package com.lalaalal.coffee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class DataTable<T> {
    protected static final ObjectMapper MAPPER = CoffeeApplication.MAPPER;
    private long lastId = 0;
    private final Map<Long, T> table;
    private final String saveFilePath;

    public DataTable(Class<T> type, String saveFilePath) {
        this.saveFilePath = saveFilePath;
        this.table = new HashMap<>();
        try (InputStream inputStream = new FileInputStream(saveFilePath)) {
            TypeFactory typeFactory = MAPPER.getTypeFactory();
            addAll(MAPPER.readValue(inputStream, typeFactory.constructCollectionType(List.class, type)));
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

    public long add(T data) {
        table.put(++lastId, data);
        return lastId;
    }

    public void addAll(Iterable<T> iterable) {
        for (T data : iterable) {
            add(data);
        }
    }

    public void remove(long id) {
        table.remove(id);
    }

    public T findFirst(Predicate<T> predicate) {
        for (T data : table.values()) {
            if (predicate.test(data))
                return data;
        }

        return null;
    }

    public T findById(long id) {
        return table.get(id);
    }

    public List<T> filter(Predicate<T> predicate) {
        return table.values().stream().filter(predicate).toList();
    }
}
