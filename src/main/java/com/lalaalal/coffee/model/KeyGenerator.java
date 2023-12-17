package com.lalaalal.coffee.model;

import lombok.Setter;

import java.util.Set;
import java.util.function.Supplier;

public abstract class KeyGenerator<K> {
    @Setter
    private Supplier<Set<K>> keySetSupplier;

    public Set<K> getKeySet() {
        return keySetSupplier.get();
    }

    public abstract K generateKey();
}
