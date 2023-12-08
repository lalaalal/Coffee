package com.lalaalal.coffee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.lalaalal.coffee.model.menu.Drink;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.model.order.OrderItem;
import com.lalaalal.coffee.registry.*;
import com.lalaalal.coffee.serializer.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoffeeApplication {
    public static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule();

    public static <T> void registerSerializer(Class<T> type, StdSerializer<T> serializer, StdDeserializer<T> deserializer) {
        MODULE.addSerializer(type, serializer);
        MODULE.addDeserializer(type, deserializer);
    }

    public static void registerRegistries() {
        Registries.register(GroupRegistry.class, GroupRegistry::new);
        Registries.register(MenuRegistry.class, MenuRegistry::new);
        Registries.register(DrinkRegistry.class, DrinkRegistry::new);
        Registries.register(OrderArgumentCreatorRegistry.class, OrderArgumentCreatorRegistry::new);
    }

    public static void initialize() {
        registerSerializer(Menu.class, new MenuSerializer(), new MenuDeserializer());
        registerSerializer(Drink.class, new DrinkSerializer(), new DrinkDeserializer());
        registerSerializer(OrderItem.class, new OrderItemSerializer(), new OrderItemDeserializer());
        registerRegistries();

        Configurations.addInitializeListener(Menu::initialize);
        Configurations.addInitializeListener(Drink::initialize);

        MAPPER.registerModule(MODULE);

        Configurations.initialize();
        Registries.initialize();
    }

    public static void main(String[] args) {
        initialize();
        SpringApplication.run(CoffeeApplication.class, args);
    }
}
