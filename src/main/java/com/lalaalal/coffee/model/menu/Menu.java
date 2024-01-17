package com.lalaalal.coffee.model.menu;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.model.order.ArgumentCreator;
import com.lalaalal.coffee.model.order.ArgumentReader;
import com.lalaalal.coffee.model.order.ArgumentWriter;
import com.lalaalal.coffee.model.order.CostModifier;
import com.lalaalal.coffee.registry.OrderArgumentCreatorRegistry;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Menu {
    public static final String ARG_COUNT = "count";

    protected static int tumblerDiscount;

    private final String id;
    private final int cost;
    private final Group group;

    public static void initialize() {
        tumblerDiscount = Configurations.getIntConfiguration("tumbler.discount");
    }

    public List<ArgumentCreator> getRequiredArgumentCreators() {
        return List.of(OrderArgumentCreatorRegistry.COUNT);
    }

    public boolean canMake(ArgumentReader arguments) {
        return true;
    }

    public List<String> getCombinationCheckArguments() {
        return List.of();
    }

    public void combineArguments(ArgumentWriter operator, ArgumentReader operand) {
        operator.combineValue(ARG_COUNT, Integer.class, operand, Integer::sum);
    }

    public int getModifiedCost(CostModifier costModifier) {
        return costModifier.apply(cost);
    }

    public int calculateCost(ArgumentReader arguments, CostModifier costModifier) {
        int count = arguments.getArgumentValue(ARG_COUNT, Integer.class);
        return getModifiedCost(costModifier) * count;
    }
}
