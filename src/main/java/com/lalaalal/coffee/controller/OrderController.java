package com.lalaalal.coffee.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController extends SessionHelper {
    public OrderController(HttpSession httpSession) {
        super(httpSession);
    }

    @GetMapping("/order-number-editor")
    public String setCurrentOrderNumberPage() {
        return "order/order-number-editor";
    }
}
