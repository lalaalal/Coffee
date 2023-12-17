package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.model.DataTable;
import com.lalaalal.coffee.model.DataTableReader;
import com.lalaalal.coffee.model.DateBasedKeyGenerator;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final DataTable<String, Order> orders;

    public OrderService() {
        String saveFilePath = Configurations.getConfiguration("data.order.path");
        DateBasedKeyGenerator keyGenerator = new DateBasedKeyGenerator();
        orders = new DataTable<>(String.class, Order.class, keyGenerator, saveFilePath);
    }

    public Result addOrder(Order order) {
        String id = orders.add(order);
        order.setId(id);

        return Result.SUCCEED;
    }

    public Result addOrderItem(String orderId, OrderItem orderItem) {
        Order order = orders.get(orderId);
        if (order == null)
            return Result.failed("result.message.failed.add_order_item", orderId);
        order.add(orderItem);
        orders.save();

        return Result.SUCCEED;
    }

    public Result cancelMenu(String orderId, String menuId) {
        Order order = orders.get(orderId);
        if (order == null)
            return Result.failed("result.message.failed.cancel_menu", orderId, menuId);
        order.remove(menuId);

        return Result.SUCCEED;
    }

    public DataTableReader<String, Order> getDataTableReader() {
        return orders;
    }
}
