package com.lalaalal.coffee.model;

import lombok.Getter;

public class Permission {
    public static final Permission NONE = new Permission("none");

    @Getter
    private final String name;
    private final Permission[] children;

    public Permission(String name, Permission... children) {
        this.name = name;
        this.children = children;
    }

    public boolean canAccess(Permission permission) {
        if (this.equals(permission))
            return true;

        for (Permission child : children) {
            if (child.canAccess(permission))
                return true;
        }

        return false;
    }
}
