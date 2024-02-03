package com.lalaalal.coffee.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminApiController extends SessionHelper {
    public AdminApiController(HttpSession httpSession) {
        super(httpSession);
    }


}
