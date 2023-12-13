package com.lalaalal.coffee.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lalaalal.coffee.model.menu.Drink;
import com.lalaalal.coffee.model.menu.Group;
import com.lalaalal.coffee.model.menu.TemperatureChecker;
import com.lalaalal.coffee.registry.GroupRegistry;
import com.lalaalal.coffee.registry.Registries;

import java.io.IOException;

public class DrinkDeserializer extends StdDeserializer<Drink> {
    public DrinkDeserializer() {
        this(Drink.class);
    }

    public DrinkDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Drink deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String id = node.get("id").asText();
        int cost = node.get("cost").asInt();
        String typeCheckerId = node.get("temperature_checker").asText();
        TemperatureChecker temperatureChecker = TemperatureChecker.get(typeCheckerId);
        String groupId = node.get("group_id").asText();
        Group group = Registries.get(GroupRegistry.class).get(groupId);

        return new Drink(id, cost, group, temperatureChecker);
    }
}
