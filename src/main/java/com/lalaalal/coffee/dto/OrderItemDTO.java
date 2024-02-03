package com.lalaalal.coffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.model.Event;
import com.lalaalal.coffee.model.order.Modifier;
import com.lalaalal.coffee.model.order.OrderItem;
import com.lalaalal.coffee.model.order.argument.OrderArgumentMap;
import lombok.Getter;

@Getter
public class OrderItemDTO {
    private final String menu;
    private final int cost;
    private final OrderArgumentMap arguments;

    @JsonCreator
    public OrderItemDTO(@JsonProperty("menu") String menu,
                        @JsonProperty("cost") int cost,
                        @JsonProperty("arguments") OrderArgumentMap arguments) {
        this.menu = menu;
        this.cost = cost;
        this.arguments = arguments;
    }

    public static OrderItemDTO convertFrom(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getMenuId(),
                orderItem.calculateCost(),
                orderItem.getArguments()
        );
    }

    public OrderItem convertToOrderItem(Event event) {
        if (event == null)
            return new OrderItem(menu, arguments);
        Modifier costModifier = event.getCostModifiers().get(menu);
        return new OrderItem(menu, costModifier, event.getArgumentCostModifier(), arguments);
    }
}
