package com.lalaalal.coffee.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.DataTable;
import com.lalaalal.coffee.DateBasedKeyGenerator;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private static final ObjectMapper MAPPER = CoffeeApplication.MAPPER;
    private final DataTable<String, Order> orders;

    public OrderService() {
        String saveFilePath = Configurations.getConfiguration("data.order.path");
        orders = new DataTable<>(String.class, Order.class, new DateBasedKeyGenerator(), saveFilePath);
    }

    public void addOrderItem(String id, String orderItemText) {
        try {
            Order order = orders.findById(id);
            OrderItem orderItem = MAPPER.readValue(orderItemText, OrderItem.class);
            order.add(orderItem);
            orders.save();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
