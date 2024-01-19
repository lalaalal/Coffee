package com.lalaalal.coffee.misc;

import java.util.Set;

public class IntegerKeyGenerator extends KeyGenerator<Integer> {
    @Override
    public Integer generateKey() {
        Set<Integer> keys = getKeySet();
        int max = keys.stream()
                .max(Integer::compareTo)
                .orElse(0);

        return max + 1;
    }
}
