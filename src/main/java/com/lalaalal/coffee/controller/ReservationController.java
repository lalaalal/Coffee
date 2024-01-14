package com.lalaalal.coffee.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/reservation")
public class ReservationController extends SessionHelper {
    @Autowired
    public ReservationController(HttpSession httpSession) {
        super(httpSession);
    }

    @GetMapping("/view")
    public String selectDate(Model model, @RequestParam("offset") int offset) {
        return "reservation/view";
    }

    @GetMapping("/make/{date}")
    public String makeReservation(Model model, @PathVariable LocalDate date) {
        return "reservation/make";
    }
}
