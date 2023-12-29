package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.model.Permission;

public class PermissionRegistry extends Registry<Permission> {
    @Override
    public void initialize() {
        register(Permission.NONE);
    }

    public void register(String name, String... childrenNames) {
        Permission[] children = new Permission[childrenNames.length];
        for (int index = 0; index < children.length; index++)
            children[index] = get(childrenNames[index]);

        register(new Permission(name, children));
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
