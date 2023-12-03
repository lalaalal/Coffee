package com.lalaalal.coffee.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.model.Drink;
import com.lalaalal.coffee.model.TypeChecker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DrinkRegistry extends Registry<Drink> {
    private final ObjectMapper mapper = CoffeeApplication.MAPPER;

    @Override
    public void initialize() {
        String drinksPath = Configurations.getConfiguration("drinks.path");
        if (!Files.exists(Path.of(drinksPath)))
            return;
        try (InputStream inputStream = new FileInputStream(drinksPath)) {
            TypeFactory typeFactory = mapper.getTypeFactory();
            List<Drink> drinks = mapper.readValue(inputStream, typeFactory.constructCollectionType(List.class, Drink.class));
            for (Drink drink : drinks)
                register(drink.getId(), drink);
        } catch (IOException e) {
            // TODO: 12/3/23 handle exception
        }
    }

    public void register(String name, int cost, TypeChecker checker) {
        register(name, new Drink(name, cost, checker));
    }
}
