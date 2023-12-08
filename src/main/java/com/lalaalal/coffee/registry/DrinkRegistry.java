package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.model.Drink;

public class DrinkRegistry extends Registry<Drink> {
    @Override
    public void initialize() {
        String filePath = Configurations.getConfiguration("menu.drinks.path");
        loadListFromJson(filePath, Drink.class, this::register);
    }

    @Override
    public void register(String key, Drink value) {
        Registries.get(MenuRegistry.class).register(key, value);
        super.register(key, value);
    }

    public void register(Drink drink) {
        register(drink.getId(), drink);
    }
}
