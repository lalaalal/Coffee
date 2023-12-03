package com.lalaalal.coffee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.lalaalal.coffee.model.Drink;
import com.lalaalal.coffee.model.OrderItem;
import com.lalaalal.coffee.registry.Registries;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoffeeApplication {
	public static final ObjectMapper MAPPER = new ObjectMapper();

	public static <T> void registerSerializer(Class<T> type, StdSerializer<T> serializer, StdDeserializer<T> deserializer) {
		SimpleModule module = new SimpleModule();
		module.addSerializer(type, serializer);
		module.addDeserializer(type, deserializer);
		MAPPER.registerModule(module);
	}

	public static void initialize() {
		registerSerializer(Drink.class, new DrinkSerializer(), new DrinkDeserializer());

		Configurations.addInitializeListener(OrderItem::initialize);
		Configurations.initialize();
		Registries.initialize();
	}

	public static void main(String[] args) {
		initialize();
		SpringApplication.run(CoffeeApplication.class, args);
	}
}
