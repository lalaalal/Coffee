package com.lalaalal.coffee.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.DayOfWeek;

public class DayOfWeekDeserializer extends StdDeserializer<DayOfWeek> {
    public DayOfWeekDeserializer() {
        this(DayOfWeek.class);
    }

    public DayOfWeekDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DayOfWeek deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String text = node.asText();

        return DayOfWeek.valueOf(text.toUpperCase());
    }
}
