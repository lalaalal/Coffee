package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.model.order.CostModifier;

public class ModifierMethodRegistry extends Registry<CostModifier.Method> {
    @Override
    public void initialize() {
        register("set", (original, factor) -> factor);
        register("add", Math::addExact);
        register("subtract", Math::subtractExact);
        register("multiply", Math::multiplyExact);
        register("divide", Math::floorDiv);
        register("do_nothing", (original, factor) -> original);
        alias("do_nothing", "null");
    }

    @Override
    public CostModifier.Method get(String key) {
        if (!registry.containsKey(key))
            throw new RuntimeException("No such key in ModifierMethodRegistry : " + key);
        return super.get(key);
    }
}
