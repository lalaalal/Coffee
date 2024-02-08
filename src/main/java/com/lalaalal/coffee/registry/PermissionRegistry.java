package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.model.Permission;

import java.util.Set;
import java.util.stream.Collectors;

public class PermissionRegistry extends Registry<Permission> {
    @Override
    public void initialize() {
        register(Permission.NONE);
        register("read.reservation.name");
        register("read.reservation.contact");
        registerByName("read.reservation");
        registerByName("read");

        register("edit.reservation");
        registerByName("edit");

        register("delete.reservation");
        register("delete");

        register(Permission.ADMIN);
    }

    public void register(String name, String... childrenName) {
        Permission[] children = new Permission[childrenName.length];
        for (int index = 0; index < children.length; index++)
            children[index] = get(childrenName[index]);

        register(new Permission(name, children));
    }

    public void registerByName(String name) {
        int depth = name.split("\\.").length;
        Set<Permission> permissions = keys().stream()
                .filter(key -> key.contains(name) && key.split("\\.").length == depth + 1)
                .map(this::get)
                .collect(Collectors.toSet());
        register(new Permission(name, permissions));
    }

    public void register(Permission permission) {
        register(permission.getName(), permission);
    }

    @Override
    public Permission get(String key) {
        if (registry.containsKey(key))
            return super.get(key);
        return get("none");
    }
}
