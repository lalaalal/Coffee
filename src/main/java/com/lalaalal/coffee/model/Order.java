package com.lalaalal.coffee.model;

import java.util.ArrayList;

public class Order extends ArrayList<OrderItem> {

    public int calculateCost() {
        int cost = 0;
        for (OrderItem orderItem : this)
            cost += orderItem.calculateCost();

        return cost;
    }
}
