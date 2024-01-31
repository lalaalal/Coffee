package com.lalaalal.coffee.model.menu;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.model.order.Modifier;
import com.lalaalal.coffee.model.order.argument.ArgumentCostModifier;
import com.lalaalal.coffee.model.order.argument.ArgumentReader;
import com.lalaalal.coffee.model.order.argument.ArgumentWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

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

    public String getTranslationKey() {
        return "menu." + id;
    }

    public Set<String> getRequiredArgumentNames() {
        return Set.of(ARG_COUNT);
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

    public int getModifiedCost(Modifier modifier) {
        if (modifier == null)
            return cost;
        return modifier.apply(cost);
    }

    public int calculateCost(ArgumentReader arguments, Modifier modifier, ArgumentCostModifier argumentCostModifier) {
        int count = arguments.getArgumentValue(ARG_COUNT, Integer.class);
        return getModifiedCost(modifier) * count;
    }
}
