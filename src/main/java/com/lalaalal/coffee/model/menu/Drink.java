package com.lalaalal.coffee.model.menu;

import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.initializer.Initialize;
import com.lalaalal.coffee.model.order.Modifier;
import com.lalaalal.coffee.model.order.argument.ArgumentCostModifier;
import com.lalaalal.coffee.model.order.argument.ArgumentReader;
import com.lalaalal.coffee.model.order.argument.ArgumentWriter;

import java.util.List;
import java.util.Set;

public class Drink extends Menu {
    public static final String ARG_SHOT = "shot";
    public static final String ARG_DECAFFEINATED = "decaffeinated";
    public static final String ARG_TUMBLER_COUNT = "tumbler_count";
    public static final String ARG_TEMPERATURE = "temperature";

    protected static int shotCost;
    protected static int decaffeinateCost;

    private final TemperatureChecker temperatureChecker;

    public Drink(String id, int cost, Group group, TemperatureChecker temperatureChecker) {
        super(id, cost, group);
        this.temperatureChecker = temperatureChecker;
    }

    @Initialize(with = Configurations.class)
    public static void initialize() {
        shotCost = Configurations.getIntConfiguration("shot.cost");
        decaffeinateCost = Configurations.getIntConfiguration("decaffeinate.cost");
    }

    @Override
    public Set<String> getRequiredArgumentNames() {
        return Set.of(
                ARG_COUNT,
                ARG_SHOT,
                ARG_DECAFFEINATED,
                ARG_TUMBLER_COUNT,
                ARG_TEMPERATURE
        );
    }

    @Override
    public boolean canMake(ArgumentReader arguments) {
        Temperature temperature = arguments.getArgumentValue(ARG_TEMPERATURE, Temperature.class);
        return super.canMake(arguments) && canMake(temperature);
    }

    public boolean canMake(Temperature temperature) {
        return temperatureChecker.canMake(temperature);
    }

    @Override
    public List<String> getCombinationCheckArguments() {
        return List.of(ARG_TEMPERATURE, ARG_SHOT, ARG_DECAFFEINATED);
    }

    @Override
    public void combineArguments(ArgumentWriter operator, ArgumentReader operand) {
        super.combineArguments(operator, operand);
        operator.combineValue(ARG_TUMBLER_COUNT, Integer.class, operand, Integer::sum);
    }

    public Temperature[] getAvailableTemperature() {
        return temperatureChecker.getAvailableTemperatures();
    }

    public String getTypeCheckerId() {
        return temperatureChecker.name();
    }

    @Override
    public int calculateCost(ArgumentReader arguments, Modifier modifier, ArgumentCostModifier argumentCostModifier) {
        int count = arguments.getArgumentValue(ARG_COUNT, Integer.class);
        int tumblerCount = arguments.getArgumentValue(ARG_TUMBLER_COUNT, Integer.class);
        boolean decaffeinated = arguments.getArgumentValue(ARG_DECAFFEINATED, Boolean.class);
        int shot = arguments.getArgumentValue(ARG_SHOT, Integer.class);
        int modifiedShotCost = argumentCostModifier.apply(ArgumentCostModifier.SHOT, shotCost);
        int modifiedDecaffeinateCost = argumentCostModifier.apply(ArgumentCostModifier.DECAFFEINATE, decaffeinateCost);
        int modifiedTumblerDiscount = argumentCostModifier.apply(ArgumentCostModifier.TUMBLER, tumblerCount);
        return (getModifiedCost(modifier) + shot * modifiedShotCost) * count
                - (decaffeinated ? modifiedDecaffeinateCost : 0)
                - tumblerCount * modifiedTumblerDiscount;
    }
}
