package com.lalaalal.coffee.model;

import com.lalaalal.coffee.Permission;

public interface Accessor {
    String whoami();

    Permission getPermission();

    default boolean canAccess(Permission permission) {
        return getPermission().canAccess(permission);
    }
}
