package com.lalaalal.coffee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBootApplication
class CoffeeApplicationTests {
	@BeforeEach
	void setUp() {
		CoffeeApplication.initialize();
	}

	@Test
	void contextLoads() {

	}

	public static void main(String[] args) {
		CoffeeApplication.initialize();
		SpringApplication.run(CoffeeApplicationTests.class, args);
	}
}
