package com.lalaalal.coffee.misc;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lalaalal.coffee.model.OrderItem;

import java.io.IOException;

public class OrderItemDeserializer extends StdDeserializer<OrderItem> {
    public OrderItemDeserializer() {
        this(OrderItem.class);
    }

    public OrderItemDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OrderItem deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String menu = node.get("menu").asText();
        OrderItem orderItem = new OrderItem(menu);

        JsonNode argumentsNode = node.get("arguments");
        orderItem.deserializeArguments(argumentsNode);

        return orderItem;
    }
}
