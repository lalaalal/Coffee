package com.lalaalal.coffee.model;

import com.lalaalal.coffee.Configurations;
import lombok.Data;

@Data
public class OrderItem {
    private static int shotCost;
    private static int decaffeinateCost;
    private static int tumblerDiscount;

    private Drink drink;
    private Type type;
    private int count;
    private int shot;
    private boolean decaffeinate;
    private boolean hasTumbler;

    public static void initialize() {
        shotCost = Configurations.getIntConfiguration("shot.cost");
        decaffeinateCost = Configurations.getIntConfiguration("decaffeinate.cost");
        tumblerDiscount = Configurations.getIntConfiguration("tumbler.discount");
    }

    public int calculateCost() {
        return drink.getCost()
                - tumblerDiscount * (hasTumbler ? 1 : 0)
                + shot * shotCost
                + decaffeinateCost * (decaffeinate ? 1 : 0);
    }
}
