package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.model.Event;
import com.lalaalal.coffee.service.EventService;
import com.lalaalal.coffee.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController extends SessionHelper {
    private final OrderService orderService;
    private final EventService eventService;

    @Autowired
    public HomeController(HttpSession httpSession, EventService eventService, OrderService orderService) {
        super(httpSession);
        this.eventService = eventService;
        this.orderService = orderService;
    }

    @GetMapping({"/", "/home"})
    public String index(Model model) {
        Event event = eventService.getCurrentEvent();
        model.addAttribute("event", event);
        model.addAttribute("currentOrderNumber", orderService.getCurrentOrderNumber());

        return "index";
    }

    @GetMapping("/menu")
    public String menu() {


        return "menu";
    }
}
