package com.lalaalal.coffee.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class RootController extends SessionHelper {

    @Autowired
    public RootController(HttpSession httpSession) {
        super(httpSession);
    }

    @GetMapping({"/", "/home"})
    public String index(Model model) {
        ArrayList<String> events = new ArrayList<>();
        events.add("hello");
        events.add("world");
        model.addAttribute("events", events);

        return "index";
    }
}
