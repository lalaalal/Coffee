package com.lalaalal.coffee.model.order;

import java.util.ArrayList;

public class Order {
    private final ArrayList<OrderItem> items = new ArrayList<>();

    public void add(OrderItem item) {
        for (OrderItem element : items) {
            if (element.canBeCombinedWith(item)) {
                element.combineWith(item);
                return;
            }
        }
        items.add(item);
    }

    public void remove(int itemId) {
        items.remove(itemId);
    }

    public int calculateCost() {
        int cost = 0;
        for (OrderItem item : items)
            cost += item.calculateCost();

        return cost;
    }
}
