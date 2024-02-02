package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.model.menu.Menu;

public class MenuRegistry extends Registry<Menu> {
    @Override
    public void initialize() {
        String filePath = Configurations.getConfiguration("menu.path");
        loadListFromJson(filePath, Menu.class, this::register);
    }

    @Override
    public void register(String key, Menu value) {
        if (value.getGroup() == null)
            // TODO: 12/5/23 handle exception
            throw new RuntimeException("group [%s] does not exists.");
        value.getGroup().add(value);
        super.register(key, value);
    }

    public void register(Menu menu) {
        register(menu.getId(), menu);
    }
}
