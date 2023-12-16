package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class Order {
    @Setter
    private String id = "";
    protected final ArrayList<OrderItem> items;

    public Order() {
        this.items = new ArrayList<>();
    }

    @JsonCreator
    public Order(@JsonProperty("id") String id, @JsonProperty("items") ArrayList<OrderItem> items) {
        this.id = id;
        this.items = items;
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
