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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("api/order")
public class OrderApiController extends AbstractController {
    public final ObjectMapper mapper = CoffeeApplication.MAPPER;
    private final OrderService orderService;

    @Autowired
    public OrderApiController(OrderService orderService, UserService userService) {
        super(userService);
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public ResponseEntity<String> listOrder() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        String text = orderService.getDataTableReader().toJsonString();

        return new ResponseEntity<>(text, headers, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResultDTO> createOrder(@RequestParam String orderJsonString) throws JsonProcessingException {
        // TODO: 12/17/23 handle exception
        Order order = mapper.readValue(orderJsonString, Order.class);
        Result result = orderService.addOrder(order);

        return createResultEntity(result);
    }

    @PostMapping("/{orderId}/menu/add")
    public ResponseEntity<ResultDTO> addOrderItem(@PathVariable String orderId, @RequestParam String orderItemString) throws JsonProcessingException {
        // TODO: 12/17/23 handle exception
        OrderItem orderItem = mapper.readValue(orderItemString, OrderItem.class);
        Result result = orderService.addOrderItem(orderId, orderItem);

        return createResultEntity(result);
    }

    @PostMapping("/{orderId}/menu/cancel")
    public ResponseEntity<ResultDTO> cancelMenu(@PathVariable String orderId, @RequestParam String menuId) {
        Result result = orderService.cancelMenu(orderId, menuId);

        return createResultEntity(result);
    }
}
