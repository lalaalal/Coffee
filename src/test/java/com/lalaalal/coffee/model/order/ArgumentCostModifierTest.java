package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.model.order.argument.ArgumentCostModifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ArgumentCostModifierTest {
    @Test
    void serializeTest() throws JsonProcessingException {
        HashMap<String, Modifier> modifiers = new HashMap<>(Map.of("shot", Modifier.DO_NOTHING));
        ArgumentCostModifier argumentCostModifier = new ArgumentCostModifier(modifiers);
        String serialized = CoffeeApplication.MAPPER.writeValueAsString(argumentCostModifier);
        System.out.println(serialized);
        ArgumentCostModifier deserialized = CoffeeApplication.MAPPER.readValue(serialized, ArgumentCostModifier.class);
        System.out.println(deserialized);
    }
}