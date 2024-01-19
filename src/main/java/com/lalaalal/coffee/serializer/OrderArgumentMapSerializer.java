package com.lalaalal.coffee.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.lalaalal.coffee.model.order.argument.OrderArgumentMap;

import java.io.IOException;

public class OrderArgumentMapSerializer extends StdSerializer<OrderArgumentMap> {
    public OrderArgumentMapSerializer() {
        this(OrderArgumentMap.class);
    }

    public OrderArgumentMapSerializer(Class<OrderArgumentMap> t) {
        super(t);
    }

    @Override
    public void serialize(OrderArgumentMap value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        value.serializeArguments(generator);
        generator.writeEndObject();
    }
}
