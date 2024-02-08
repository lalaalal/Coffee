package com.lalaalal.coffee.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class UserController extends SessionHelper {
    @Autowired
    public UserController(HttpSession httpSession) {
        super(httpSession);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
