package com.lalaalal.coffee.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.lalaalal.coffee.model.menu.Menu;

import java.io.IOException;

public class MenuSerializer extends StdSerializer<Menu> {
    public MenuSerializer() {
        this(Menu.class);
    }

    public MenuSerializer(Class<Menu> t) {
        super(t);
    }

    @Override
    public void serialize(Menu value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeStringField("id", value.getId());
        generator.writeNumberField("cost", value.getCost());
        generator.writeStringField("group_id", value.getGroup().getId());
        generator.writeEndObject();
    }
}
