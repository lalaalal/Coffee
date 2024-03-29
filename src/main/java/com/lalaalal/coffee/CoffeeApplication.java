package com.lalaalal.coffee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lalaalal.coffee.config.BusinessHours;
import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.exception.FatalError;
import com.lalaalal.coffee.model.menu.Drink;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.model.order.argument.OrderArgumentMap;
import com.lalaalal.coffee.registry.*;
import com.lalaalal.coffee.serializer.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.DayOfWeek;

@Slf4j
@SpringBootApplication
public class CoffeeApplication {
    public static final ObjectMapper MAPPER = new ObjectMapper();
    private static final SimpleModule MODULE = new SimpleModule();

    public static <T> void registerSerializer(Class<T> type, StdSerializer<T> serializer, StdDeserializer<T> deserializer) {
        MODULE.addSerializer(type, serializer);
        MODULE.addDeserializer(type, deserializer);
    }

    public static void registerRegistries() {
        Registries.register(ModifierMethodRegistry.class, ModifierMethodRegistry::new);
        Registries.register(PermissionRegistry.class, PermissionRegistry::new);
        Registries.register(LanguageRegistry.class, LanguageRegistry::new);
        Registries.register(GroupRegistry.class, GroupRegistry::new);
        Registries.register(MenuRegistry.class, MenuRegistry::new);
        Registries.register(DrinkRegistry.class, DrinkRegistry::new);
        Registries.register(OrderArgumentCreatorRegistry.class, OrderArgumentCreatorRegistry::new);
        Registries.register(TimeRangeRegistry.class, TimeRangeRegistry::new);
    }

    public static void initialize() throws FatalError {
        log.info("Initializing before Spring Boot");
        MAPPER.registerModule(new JavaTimeModule());
        registerSerializer(Menu.class, new MenuSerializer(), new MenuDeserializer());
        registerSerializer(Drink.class, new DrinkSerializer(), new DrinkDeserializer());
        registerSerializer(OrderArgumentMap.class, new OrderArgumentMapSerializer(), new OrderArgumentMapDeserializer());
        MODULE.addDeserializer(DayOfWeek.class, new DayOfWeekDeserializer());
        registerRegistries();

        MAPPER.registerModule(MODULE);

        Configurations.initialize();
        Registries.initialize();
    }

    @Bean
    public static ObjectMapper objectMapper() {
        return MAPPER;
    }

    @Bean
    public static BusinessHours businessHours() {
        return BusinessHours.getInstance();
    }

    public static void main(String[] args) {
        try {
            initialize();
            SpringApplication.run(CoffeeApplication.class, args);
        } catch (Throwable throwable) {
            log.error(throwable.getMessage());
            Throwable cause = throwable.getCause();
            if (cause != null)
                log.error("cause : {}", cause.getMessage());
            log.error("Boot failed!");
        }
    }
}
