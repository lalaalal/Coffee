package com.lalaalal.coffee.misc;

@FunctionalInterface
public interface DelegateGetter<K, V> {
    V get(K key);
}
