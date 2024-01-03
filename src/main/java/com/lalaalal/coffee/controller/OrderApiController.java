package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.exception.ClientCausedException;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.OrderItem;
import com.lalaalal.coffee.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/order")
@SuppressWarnings("unused")
public class OrderApiController extends BaseController {
    private final OrderService orderService;

    @Autowired
    public OrderApiController(OrderService orderService, HttpSession httpSession) {
        super(httpSession);
        this.orderService = orderService;
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Collection<Order>> listOrder() {
        return createResponseEntity(orderService.collect(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResultDTO> createOrder(@RequestBody Order order) {
        for (OrderItem item : order.getItems()) {
            if (!item.canMake())
                // TODO: 12/28/23 add translation
                throw new ClientCausedException("error.client.message.unable_to_make_menu", item.getMenuId());
        }
        Result result = orderService.addOrder(order);

        return createResultEntity(result);
    }

    @RequestMapping(value = "/{orderId}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") String orderId) {
        if (!orderService.isValidKey(orderId))
            // TODO: 12/28/23 add translation
            throw new ClientCausedException("error.client.message.no_such_order_id", orderId);
        Order order = orderService.getOrder(orderId);

        return createResponseEntity(order, HttpStatus.OK);
    }

    @PostMapping("/{orderId}/menu/add")
    public ResponseEntity<ResultDTO> addOrderItem(
            @PathVariable("orderId") String orderId,
            @RequestBody OrderItem orderItem) {
        if (!orderItem.canMake())
            throw new ClientCausedException("error.client.message.unable_to_make_menu", orderItem.getMenuId());
        Result result = orderService.addOrderItem(orderId, orderItem);

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
