package com.lalaalal.coffee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBootApplication
class CoffeeApplicationTests {
	@Test
	void contextLoads() {

	}

	public static void main(String[] args) {
		SpringApplication.run(CoffeeApplicationTests.class, args);
	}
}
