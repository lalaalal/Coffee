package com.lalaalal.coffee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.model.order.Reservation;
import com.lalaalal.coffee.registry.MenuRegistry;
import com.lalaalal.coffee.registry.Registries;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class CoffeeApplicationTests {
	@BeforeEach
	void setUp() {
		CoffeeApplication.initialize();
	}

	@Test
	void contextLoads() throws JsonProcessingException {
		System.out.println(Files.currentFolder());
		for (Menu drink : Registries.get(MenuRegistry.class).values()) {
			String str = CoffeeApplication.MAPPER.writeValueAsString(drink);
			System.out.println(str);
		}
	}

	@Test
	void test() throws JsonProcessingException {
		Reservation reservation = new Reservation("a", "aa", "gj", LocalDateTime.now());
		String s = CoffeeApplication.MAPPER.writeValueAsString(reservation);
		System.out.println(s);
		System.out.println(CoffeeApplication.MAPPER.readValue(s, Reservation.class));
	}
}
