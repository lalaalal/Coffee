package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @ResponseBody
    @GetMapping(value = "reservation")
    public String makeReservation() {
        return reservationService.toString();
    }
}
