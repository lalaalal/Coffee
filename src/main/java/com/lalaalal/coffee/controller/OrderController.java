package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController extends SessionHelper {
    private final EventService eventService;

    public OrderController(HttpSession httpSession, EventService eventService) {
        super(httpSession);
        this.eventService = eventService;
    }
}
