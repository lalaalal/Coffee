package com.lalaalal.coffee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import com.lalaalal.coffee.service.OrderService;
import com.lalaalal.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("order")
public class OrderController extends AbstractController {
    public final ObjectMapper mapper = CoffeeApplication.MAPPER;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        super(userService);
        this.orderService = orderService;
    }

    @ResponseBody
    @PostMapping("create")
    public String createOrder(@RequestParam String orderString) throws JsonProcessingException {
        // TODO: 12/17/23 handle exception
        Order order = mapper.readValue(orderString, Order.class);
        Result result = orderService.addOrder(order);

        return result.translatedJsonString(userService.getLanguage());
    }

    @ResponseBody
    @PostMapping("{orderId}/menu/add")
    public ResponseEntity<String> addOrderItem(@PathVariable String orderId, @RequestParam String orderItemString) throws JsonProcessingException {
        // TODO: 12/17/23 handle exception
        OrderItem orderItem = mapper.readValue(orderItemString, OrderItem.class);
        Result result = orderService.addOrderItem(orderId, orderItem);

        return createResultEntity(result);
    }

    @ResponseBody
    @PostMapping("{orderId}/menu/cancel")
    public ResponseEntity<String> cancelMenu(@PathVariable String orderId, @RequestParam String menuId) {
        Result result = orderService.cancelMenu(orderId, menuId);

        return createResultEntity(result);
    }
}
