package com.lalaalal.coffee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalaalal.coffee.model.OrderItem;
import com.lalaalal.coffee.registry.Registries;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoffeeApplication {
	public static final ObjectMapper MAPPER = new ObjectMapper();

	public static void initialize() {
		Configurations.addInitializeListener(OrderItem::initialize);
		Configurations.initialize();
		Registries.initialize();
	}

	public static void main(String[] args) {
		initialize();
		SpringApplication.run(CoffeeApplication.class, args);
	}
}
