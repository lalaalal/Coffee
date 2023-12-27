package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.model.DataTable;
import com.lalaalal.coffee.model.DataTableReader;
import com.lalaalal.coffee.model.DateBasedKeyGenerator;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;

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

        orders.save();
        return Result.SUCCEED;
    }

    public Result addOrderItem(String orderId, OrderItem orderItem) {
        Order order = orders.get(orderId);
        if (order == null)
            return Result.failed("result.message.failed.no_such_order_id", orderId);
        order.add(orderItem);
        orders.save();

        return Result.SUCCEED;
    }

    public Result cancelMenu(String orderId, String menuId) {
        Order order = orders.get(orderId);
        if (order == null)
            return Result.failed("result.message.failed.no_such_order_id", orderId);
        if (!order.containsMenu(menuId))
            return Result.failed("result.message.failed.menu_not_exist", orderId, menuId);
        order.remove(menuId);

        return Result.SUCCEED;
    }

    public Collection<Order> collect() {
        return orders.stream()
                .sorted(Comparator.comparing(Order::getId))
                .toList();
    }

    public DataTableReader<String, Order> getDataTableReader() {
        return orders;
    }
}
