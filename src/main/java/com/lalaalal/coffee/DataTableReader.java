package com.lalaalal.coffee;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public interface DataTableReader<K, V> {
    V findFirst(Predicate<V> predicate);

    V findById(K id);

    Set<K> filterKey(Predicate<K> predicate);

    List<V> filter(Predicate<V> predicate);

    String toJSON();
}
