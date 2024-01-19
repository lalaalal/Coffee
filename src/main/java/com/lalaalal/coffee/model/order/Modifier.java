package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.registry.ModifierMethodRegistry;
import com.lalaalal.coffee.registry.Registries;
import lombok.Getter;

public class Modifier {
    public static final Modifier DO_NOTHING = new Modifier("do_nothing", 0);

    @JsonIgnore
    private final Method method;

    @Getter
    @JsonProperty("factor")
    private final int factor;

    public Modifier(Method method, int factor) {
        this.method = method;
        this.factor = factor;
    }

    @JsonCreator
    public Modifier(
            @JsonProperty("method") String method,
            @JsonProperty("factor") int factor) {
        this.method = Registries.get(ModifierMethodRegistry.class, method);
        this.factor = factor;
    }

    public int apply(int original) {
        return method.modify(original, factor);
    }

    @JsonProperty("method")
    public String getMethod() {
        ModifierMethodRegistry registry = Registries.get(ModifierMethodRegistry.class);
        return registry.findKey(method);
    }

    public interface Method {
        int modify(int original, int factor);
    }
}
