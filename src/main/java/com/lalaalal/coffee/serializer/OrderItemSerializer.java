package com.lalaalal.coffee.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.lalaalal.coffee.model.order.OrderItem;

import java.io.IOException;

public class OrderItemSerializer extends StdSerializer<OrderItem> {
    public OrderItemSerializer() {
        this(OrderItem.class);
    }

    public OrderItemSerializer(Class<OrderItem> t) {
        super(t);
    }

    @Override
    public void serialize(OrderItem value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField("menu", value.getMenuId());
        value.serializeArguments(generator);
        generator.writeEndObject();
    }
}
