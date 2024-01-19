package com.lalaalal.coffee.misc;

import lombok.Setter;

import java.util.Set;
import java.util.function.Supplier;

@Setter
public abstract class KeyGenerator<K> {
    private Supplier<Set<K>> keySetSupplier;

    public Set<K> getKeySet() {
        return keySetSupplier.get();
    }

    public abstract K generateKey();
}
