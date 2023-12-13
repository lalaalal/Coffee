package com.lalaalal.coffee.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.lalaalal.coffee.model.menu.Group;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.registry.GroupRegistry;
import com.lalaalal.coffee.registry.Registries;

import java.io.IOException;

public class MenuDeserializer extends StdDeserializer<Menu> {
    public MenuDeserializer() {
        this(Menu.class);
    }

    public MenuDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Menu deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String id = node.get("id").asText();
        int cost = node.get("cost").asInt();
        String groupId = node.get("group_id").asText();
        Group group = Registries.get(GroupRegistry.class).get(groupId);

        return new Menu(id, cost, group);
    }
}
