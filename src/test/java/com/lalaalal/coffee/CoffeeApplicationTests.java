package com.lalaalal.coffee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.registry.MenuRegistry;
import com.lalaalal.coffee.registry.Registries;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;

class CoffeeApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {
		System.out.println(Files.currentFolder());
		CoffeeApplication.initialize();
		for (Menu drink : Registries.get(MenuRegistry.class).values()) {
			String str = CoffeeApplication.MAPPER.writeValueAsString(drink);
			System.out.println(str);
		}
	}
}
