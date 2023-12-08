package com.lalaalal.coffee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalaalal.coffee.CoffeeApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReservationController {
    private final ObjectMapper mapper = CoffeeApplication.MAPPER;

    @ResponseBody
    @PostMapping(value = "reservation", params = "reservation")
    public String makeReservation(@RequestParam("reservation") String reservationStr) throws JsonProcessingException {
        //Reservation reservation = mapper.readValue(reservationStr, Reservation.class);

        return "succeed";
    }
}
