package com.lalaalal.coffee.registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.exception.FatalError;
import com.lalaalal.coffee.model.menu.Drink;
import com.lalaalal.coffee.serializer.DrinkSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DrinkRegistryTest {
    @BeforeEach
    void setUp() throws FatalError {
        Configurations.initialize();
    }

    @Test
    void initialize() throws JsonProcessingException {
        DrinkRegistry registry = new DrinkRegistry();
        registry.initialize();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Drink.class, new DrinkSerializer());
        objectMapper.registerModule(simpleModule);
        for (Drink drink : registry.values()) {
            String str = objectMapper.writeValueAsString(drink);
            System.out.println(str);
        }
    }
}