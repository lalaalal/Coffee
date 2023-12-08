package com.lalaalal.coffee.model;

import java.util.Collection;

public interface ArgumentReader {
    Collection<String> getArgumentNames();

    <T> T getArgumentValue(String name, Class<T> type);
}
