package com.lalaalal.coffee.model;

import lombok.Getter;

public class Permission {
    public static final Permission NONE = new Permission("none");
    public static final Permission ADMIN = new Permission("admin");

    @Getter
    private final String name;
    private final Permission[] children;

    public Permission(String name, Permission... children) {
        this.name = name;
        this.children = children;
    }

    public boolean canAccess(Permission permission) {
        if (this.equals(ADMIN) || this.equals(permission))
            return true;

        for (Permission child : children) {
            if (child.canAccess(permission))
                return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
