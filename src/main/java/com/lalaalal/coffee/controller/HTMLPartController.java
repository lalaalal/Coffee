package com.lalaalal.coffee.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/part")
public class HTMLPartController extends SessionHelper {

    public HTMLPartController(HttpSession httpSession) {
        super(httpSession);
    }

    @GetMapping("/header")
    public String getHeader(Model model) {
        String title = getUserLanguage().translate("app.name");
        model.addAttribute("title", title);

        return "/part/header";
    }
}
