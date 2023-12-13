package com.lalaalal.coffee.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.lalaalal.coffee.model.menu.Drink;

import java.io.IOException;

public class DrinkSerializer extends StdSerializer<Drink> {
    public DrinkSerializer() {
        this(Drink.class);
    }

    public DrinkSerializer(Class<Drink> t) {
        super(t);
    }

    @Override
    public void serialize(Drink value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField("id", value.getId());
        generator.writeNumberField("cost", value.getCost());
        generator.writeStringField("temperature_checker", value.getTypeCheckerId());
        generator.writeStringField("group_id", value.getGroup().getId());
        generator.writeEndObject();
    }
}
