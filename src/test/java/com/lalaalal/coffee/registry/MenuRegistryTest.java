package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.exception.FatalError;
import com.lalaalal.coffee.model.menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuRegistryTest {
    @BeforeEach
    void setUp() throws FatalError {
        CoffeeApplication.initialize();
    }

    @Test
    void initialize() {
        MenuRegistry registry = Registries.get(MenuRegistry.class);
        for (Menu menu : registry.values()) {
            System.out.println(menu.getId());
        }
    }
}