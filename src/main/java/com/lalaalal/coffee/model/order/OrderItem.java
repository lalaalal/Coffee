package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.model.order.argument.ArgumentCostModifier;
import com.lalaalal.coffee.model.order.argument.OrderArgumentMap;
import com.lalaalal.coffee.registry.MenuRegistry;
import com.lalaalal.coffee.registry.Registries;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({"menu", "cost_modifier", "argument_cost_modifier", "arguments"})
public class OrderItem {
    @JsonIgnore
    private final Menu menu;
    @Getter @Setter
    @JsonProperty("cost_modifier")
    private Modifier modifier = Modifier.DO_NOTHING;
    @Getter @Setter
    @JsonProperty("argument_cost_modifier")
    private ArgumentCostModifier argumentCostModifier = new ArgumentCostModifier();
    @Getter
    @JsonProperty("arguments")
    private final OrderArgumentMap arguments;

    public OrderItem(String menu) {
        this.menu = Registries.get(MenuRegistry.class, menu);
        this.arguments = new OrderArgumentMap(this.menu);
    }

    public OrderItem(String menu, OrderArgumentMap arguments) {
        this.menu = Registries.get(MenuRegistry.class, menu);
        this.arguments = arguments;
        this.arguments.ensureArguments(this.menu);
    }

    @JsonCreator
    public OrderItem(@JsonProperty("menu") String menu,
                     @JsonProperty("cost_modifier") Modifier modifier,
                     @JsonProperty("argument_cost_modifier") ArgumentCostModifier argumentCostModifier,
                     @JsonProperty("arguments") OrderArgumentMap arguments) {
        this.menu = Registries.get(MenuRegistry.class, menu);
        this.modifier = modifier;
        this.argumentCostModifier = argumentCostModifier;
        this.arguments = arguments;
        this.arguments.ensureArguments(this.menu);
    }

    @JsonProperty("menu")
    public String getMenuId() {
        return menu.getId();
    }

    public boolean canMake() {
        return menu.canMake(arguments);
    }

    public int calculateCost() {
        return menu.calculateCost(arguments, modifier, argumentCostModifier);
    }

    public boolean canBeCombinedWith(OrderItem other) {
        return this.arguments.canBeCombinedWith(other.arguments, menu);
    }

    public void combineWith(OrderItem operand) {
        menu.combineArguments(this.arguments, operand.arguments);
    }
}
