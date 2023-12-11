package com.lalaalal.coffee.model.menu;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.model.order.ArgumentCreator;
import com.lalaalal.coffee.model.order.ArgumentReader;
import com.lalaalal.coffee.model.order.ArgumentWriter;
import com.lalaalal.coffee.model.order.OrderItem;
import com.lalaalal.coffee.registry.OrderArgumentCreatorRegistry;

import java.io.IOException;
import java.util.List;

public class Drink extends Menu {
    public static final String ARG_SHOT = "shot";
    public static final String ARG_DECAFFEINATED = "decaffeinated";
    public static final String ARG_TUMBLER_COUNT = "tumblerCount";
    public static final String ARG_TEMPERATURE = "temperature";

    protected static int shotCost;
    protected static int decaffeinateCost;

    private final TemperatureChecker temperatureChecker;

    public static void initialize() {
        shotCost = Configurations.getIntConfiguration("shot.cost");
        decaffeinateCost = Configurations.getIntConfiguration("decaffeinate.cost");
    }

    public Drink(String id, int cost, Group group, TemperatureChecker temperatureChecker) {
        super(id, cost, group);
        this.temperatureChecker = temperatureChecker;
    }

    @Override
    public List<ArgumentCreator> getRequiredArgumentCreators() {
        return List.of(
                OrderArgumentCreatorRegistry.COUNT,
                OrderArgumentCreatorRegistry.SHOT,
                OrderArgumentCreatorRegistry.DECAFFEINATE,
                OrderArgumentCreatorRegistry.TUMBLER_COUNT,
                OrderArgumentCreatorRegistry.TEMPERATURE
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

    public Temperature[] getAvailableTypes() {
        return temperatureChecker.getAvailableTemperatures();
    }

    public String getTypeCheckerId() {
        return temperatureChecker.name();
    }

    @Override
    public int calculateCost(ArgumentReader arguments) {
        int count = arguments.getArgumentValue(ARG_COUNT, Integer.class);
        int tumblerCount = arguments.getArgumentValue(ARG_TUMBLER_COUNT, Integer.class);
        boolean decaffeinated = arguments.getArgumentValue(ARG_DECAFFEINATED, Boolean.class);
        int shot = arguments.getArgumentValue(ARG_SHOT, Integer.class);
        return (getCost() + shot * shotCost) * count
                - (decaffeinated ? decaffeinateCost : 0)
                - tumblerCount * tumblerDiscount;
    }

    @Override
    public void _serializeArguments(JsonGenerator generator, ArgumentReader arguments) throws IOException {
        super._serializeArguments(generator, arguments);
        int shot = arguments.getArgumentValue(ARG_SHOT, Integer.class);
        boolean decaffeinated = arguments.getArgumentValue(ARG_DECAFFEINATED, Boolean.class);
        int tumblerCount = arguments.getArgumentValue(ARG_TUMBLER_COUNT, Integer.class);
        Temperature temperature = arguments.getArgumentValue(ARG_TEMPERATURE, Temperature.class);
        generator.writeNumberField(ARG_SHOT, shot);
        generator.writeBooleanField(ARG_DECAFFEINATED, decaffeinated);
        generator.writeNumberField(ARG_TUMBLER_COUNT, tumblerCount);
        generator.writeStringField(ARG_TEMPERATURE, temperature.name());
    }

    @Override
    public void deserializeArguments(JsonNode argumentsNode, OrderItem orderItem) {
        super.deserializeArguments(argumentsNode, orderItem);
        int shot = argumentsNode.get(ARG_SHOT).asInt();
        boolean decaffeinated = argumentsNode.get(ARG_DECAFFEINATED).asBoolean();
        int tumblerCount = argumentsNode.get(ARG_TUMBLER_COUNT).asInt();
        String temperatureString = argumentsNode.get(ARG_TEMPERATURE).asText();
        Temperature temperature = Temperature.get(temperatureString);
        orderItem.setArgument(ARG_SHOT, Integer.class, shot);
        orderItem.setArgument(ARG_DECAFFEINATED, Boolean.class, decaffeinated);
        orderItem.setArgument(ARG_TUMBLER_COUNT, Integer.class, tumblerCount);
        orderItem.setArgument(ARG_TEMPERATURE, Temperature.class, temperature);
    }
}
