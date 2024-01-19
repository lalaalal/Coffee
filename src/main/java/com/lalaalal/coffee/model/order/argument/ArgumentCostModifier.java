package com.lalaalal.coffee.model.order.argument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lalaalal.coffee.model.order.Modifier;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
public class ArgumentCostModifier {
    public static final String SHOT = "shot";
    public static final String DECAFFEINATE = "decaffeinate";
    public static final String TUMBLER = "tumbler";

    @JsonIgnore
    private HashMap<String, Modifier> modifiers;

    public ArgumentCostModifier() {
        modifiers = new HashMap<>();
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ArgumentCostModifier(@JsonProperty("modifiers") HashMap<String, Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    @JsonValue
    public Map<String, Modifier> getModifiers() {
        return modifiers;
    }

    public void setArgumentCostModifier(String argumentName, Modifier modifier) {
        modifiers.put(argumentName, modifier);
    }

    public int apply(String argumentName, int original) {
        if (modifiers == null)
            return original;
        Modifier modifier = modifiers.get(argumentName);
        if (modifier == null)
            return original;
        return modifier.apply(original);
    }
}
