package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Order {
    protected final List<OrderItem> items;
    @Setter
    private String id = "";

    public Order() {
        this.items = new ArrayList<>();
    }

    @JsonCreator
    public Order(@JsonProperty("id") String id, @JsonProperty("items") List<OrderItem> items) {
        this.id = id;
        this.items = new ArrayList<>();
        this.items.addAll(items);
    }

    public void add(OrderItem item) {
        for (OrderItem element : items) {
            if (element.canBeCombinedWith(item)) {
                element.combineWith(item);
                return;
            }
        }
        items.add(item);
    }

    public boolean containsMenu(String menuName) {
        return items.stream()
                .anyMatch(orderItem -> orderItem.getMenuId().equals(menuName));
    }

    public void remove(String menuId) {
        items.removeIf(item -> item.getMenuId().equals(menuId));
    }

    public int calculateCost() {
        int cost = 0;
        for (OrderItem item : items)
            cost += item.calculateCost();

        return cost;
    }
}
