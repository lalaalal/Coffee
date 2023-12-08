package com.lalaalal.coffee.model.order;

import java.util.Collection;

public interface ArgumentReader {
    Collection<String> getArgumentNames();

    <T> T getArgumentValue(String name, Class<T> type);
}
