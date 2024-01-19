package com.lalaalal.coffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.model.Event;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderDTO {
    private final String id;
    private final int cost;
    private final List<OrderItemDTO> items;

    @JsonCreator
    public OrderDTO(@JsonProperty("id") String id,
                    @JsonProperty("cost") int cost,
                    @JsonProperty("items") List<OrderItemDTO> items) {
        this.id = id;
        this.cost = cost;
        this.items = items;
    }

    public Order convertToOrder(Event event) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO item : items)
            orderItems.add(item.convertToOrderItem(event));

        return new Order(id, orderItems);
    }

    public static OrderDTO convertFrom(Order order) {
        ArrayList<OrderItemDTO> items = new ArrayList<>();
        for (OrderItem item : order.getItems())
            items.add(OrderItemDTO.convertFrom(item));

        return new OrderDTO(
                order.getId(),
                order.calculateCost(),
                items
        );
    }
}
