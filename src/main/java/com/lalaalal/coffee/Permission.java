package com.lalaalal.coffee;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Permission {
    public static final Permission NONE = new Permission("none");
    public static final Permission ADMIN = new Permission("admin");

    @Getter
    private final String name;
    private final Set<Permission> children;

    public Permission(String name, Permission... children) {
        this.name = name;
        this.children = new HashSet<>();
        this.children.addAll(Arrays.asList(children));
    }

    public Permission(String name, Set<Permission> children) {
        this.name = name;
        this.children = new HashSet<>();
        this.children.addAll(children);
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

    @Override
    public String toString() {
        return name;
    }
}
