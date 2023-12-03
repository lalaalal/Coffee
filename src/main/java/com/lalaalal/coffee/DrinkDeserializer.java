package com.lalaalal.coffee;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lalaalal.coffee.model.Drink;
import com.lalaalal.coffee.model.TypeChecker;

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
        String typeCheckerId = node.get("typeChecker").asText();
        TypeChecker typeChecker = TypeChecker.get(typeCheckerId);

        return new Drink(id, cost, typeChecker);
    }
}
