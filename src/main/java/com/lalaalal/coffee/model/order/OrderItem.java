package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.registry.MenuRegistry;
import com.lalaalal.coffee.registry.Registries;
import lombok.Getter;
import lombok.Setter;

@JsonPropertyOrder({"menu", "cost_modifier", "arguments"})
public class OrderItem {
    @JsonIgnore
    private final Menu menu;
    @Getter
    @Setter
    @JsonProperty("cost_modifier")
    private CostModifier costModifier = CostModifier.DO_NOTHING;
    @Getter
    @JsonProperty("arguments")
    private final OrderArgumentMap arguments;

    public OrderItem(String menu) {
        this.menu = Registries.get(MenuRegistry.class, menu);
        this.arguments = new OrderArgumentMap(this.menu);
    }

    @JsonCreator
    public OrderItem(@JsonProperty("menu") String menu,
                     @JsonProperty("cost_modifier") CostModifier costModifier,
                     @JsonProperty("arguments") OrderArgumentMap arguments) {
        this.menu = Registries.get(MenuRegistry.class, menu);
        this.costModifier = costModifier;
        this.arguments = arguments;
    }

    @JsonProperty("menu")
    public String getMenuId() {
        return menu.getId();
    }

    public boolean canMake() {
        return menu.canMake(arguments);
    }

    public int calculateCost() {
        return menu.calculateCost(arguments, costModifier);
    }

    public boolean canBeCombinedWith(OrderItem other) {
        return this.arguments.canBeCombinedWith(other.arguments, menu);
    }

    public void combineWith(OrderItem operand) {
        menu.combineArguments(this.arguments, operand.arguments);
    }
}
