package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.dto.OrderDTO;
import com.lalaalal.coffee.dto.OrderItemDTO;
import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.exception.ClientCausedException;
import com.lalaalal.coffee.model.Event;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.service.EventService;
import com.lalaalal.coffee.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/order")
public class OrderApiController extends SessionHelper {
    private final OrderService orderService;
    private final EventService eventService;

    @Autowired
    public OrderApiController(
            OrderService orderService,
            EventService eventService,
            HttpSession httpSession) {
        super(httpSession);
        this.orderService = orderService;
        this.eventService = eventService;
    }

    @GetMapping("/current")
    public int getCurrentOrderNumber() {
        return orderService.getCurrentOrderNumber();
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Collection<OrderDTO>> listOrder() {
        return createResponseEntity(orderService.collectDTO(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResultDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        Event event = eventService.getCurrentEvent();
        Result result = orderService.addOrder(orderDTO, event);

        return createResultEntity(result);
    }

    @RequestMapping(value = "/{orderId}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("orderId") String orderId) {
        if (!orderService.isValidKey(orderId))
            // TODO: 12/28/23 add translation
            throw new ClientCausedException("error.client.message.no_such_order_id", orderId);
        OrderDTO order = orderService.getOrder(orderId);

        return createResponseEntity(order, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/menu/add")
    public ResponseEntity<ResultDTO> addOrderItem(
            @PathVariable("orderId") String orderId,
            @RequestBody OrderItemDTO orderItem) {
        Event event = eventService.getCurrentEvent();
        Result result = orderService.addOrderItem(orderId, orderItem, event);

        return createResultEntity(result);
    }

    @PostMapping("/{orderId}/menu/{menuId}/cancel")
    public ResponseEntity<ResultDTO> cancelMenu(
            @PathVariable("orderId") String orderId,
            @PathVariable("menuId") String menuId) {
        Result result = orderService.cancelMenu(orderId, menuId);

        return createResultEntity(result);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ResultDTO> cancelOrder(@PathVariable("orderId") String orderId) {
        Result result = orderService.cancelOrder(orderId);

        return createResultEntity(result);
    }
}
