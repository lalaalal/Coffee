package com.lalaalal.coffee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalaalal.coffee.*;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private static final ObjectMapper MAPPER = CoffeeApplication.MAPPER;
    private final DataTable<String, Order> orders;

    public OrderService() {
        String saveFilePath = Configurations.getConfiguration("data.order.path");
        DateBasedKeyGenerator keyGenerator = new DateBasedKeyGenerator();
        orders = new DataTable<>(String.class, Order.class, keyGenerator, saveFilePath);
    }

    public void addOrder(Order order) {
        String id = orders.add(order);
        order.setId(id);
    }

    public void addOrderItem(String id, OrderItem orderItem) {
        Order order = orders.findById(id);
        order.add(orderItem);
        orders.save();
    }

    public void cancelOrderItem(String id, String menu) {
        Order order = orders.findById(id);
        order.remove(menu);
    }

    public DataTableReader<String, Order> getDataTableReader() {
        return orders;
    }
}
