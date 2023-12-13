package com.lalaalal.coffee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lalaalal.coffee.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @ResponseBody
    @PostMapping(value = "reservation", params = "reservation")
    public String makeReservation(@RequestParam("reservation") String reservationStr) throws JsonProcessingException {
        //Reservation reservation = mapper.readValue(reservationStr, Reservation.class);

        return "succeed";
    }
}
