package com.lalaalal.coffee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.ResultDTO;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import com.lalaalal.coffee.service.OrderService;
import com.lalaalal.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/order")
public class OrderApiController extends BaseController {
    public final ObjectMapper mapper = CoffeeApplication.MAPPER;
    private final OrderService orderService;

    @Autowired
    public OrderApiController(OrderService orderService, UserService userService) {
        super(userService);
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public ResponseEntity<Collection<Order>> listOrder() {
        return createResponseEntity(orderService.collect(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResultDTO> createOrder(@RequestBody Order order) {
        // TODO: 12/17/23 handle exception
        Result result = orderService.addOrder(order);

        return createResultEntity(result);
    }

    @PostMapping("/{orderId}/menu/add")
    public ResponseEntity<ResultDTO> addOrderItem(@PathVariable("orderId") String orderId, @RequestBody OrderItem orderItem) throws JsonProcessingException {
        // TODO: 12/17/23 handle exception
        Result result = orderService.addOrderItem(orderId, orderItem);

        return createResultEntity(result);
    }

    @PostMapping("/{orderId}/menu/{menuId}/cancel")
    public ResponseEntity<ResultDTO> cancelMenu(@PathVariable("orderId") String orderId, @PathVariable("menuId") String menuId) {
        Result result = orderService.cancelMenu(orderId, menuId);

        return createResultEntity(result);
    }
}
