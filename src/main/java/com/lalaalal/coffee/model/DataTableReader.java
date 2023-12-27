package com.lalaalal.coffee.model;

import java.util.Collection;
import java.util.stream.Stream;

public interface DataTableReader<K, V> {
    V get(K key);

    Collection<V> collect();

    Stream<V> stream();
}
