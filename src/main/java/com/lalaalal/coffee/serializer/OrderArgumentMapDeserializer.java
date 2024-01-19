package com.lalaalal.coffee.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lalaalal.coffee.model.order.argument.OrderArgumentMap;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class OrderArgumentMapDeserializer extends StdDeserializer<OrderArgumentMap> {
    public OrderArgumentMapDeserializer() {
        this(OrderArgumentMap.class);
    }

    public OrderArgumentMapDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OrderArgumentMap deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        Set<String> argumentNames = new HashSet<>();
        node.fieldNames().forEachRemaining(argumentNames::add);
        OrderArgumentMap arguments = new OrderArgumentMap(argumentNames);
        arguments.deserializeArguments(node);

        return arguments;
    }
}
