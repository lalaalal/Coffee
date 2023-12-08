package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.model.Group;

public class GroupRegistry extends Registry<Group> {
    @Override
    public void initialize() {
        register("etc");

        String filePath = Configurations.getConfiguration("menu.groups.path");
        loadListFromJson(filePath, String.class, this::register);
    }

    @Override
    public Group get(String key) {
        if (registry.containsKey(key))
            return super.get(key);
        return super.get("etc");
    }

    public void register(String id) {
        register(id, new Group(id));
    }
}
