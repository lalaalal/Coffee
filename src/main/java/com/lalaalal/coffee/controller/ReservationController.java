package com.lalaalal.coffee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.model.DataTableReader;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.ReservationDTO;
import com.lalaalal.coffee.service.OrderService;
import com.lalaalal.coffee.service.ReservationService;
import com.lalaalal.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("reservation")
public class ReservationController {
    private final ObjectMapper mapper = CoffeeApplication.MAPPER;
    private final ReservationService reservationService;
    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public ReservationController(ReservationService reservationService, OrderService orderService, UserService userService) {
        this.reservationService = reservationService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/make")
    public String makeReservation() {
        return "reservation";
    }

    @ResponseBody
    @PostMapping("/take")
    public String takeReservation(@RequestParam String reservation, @RequestParam String password) throws JsonProcessingException {
        Language language = userService.getLanguage();
        ReservationDTO reservationDTO = mapper.readValue(reservation, ReservationDTO.class);
        orderService.addOrder(reservationDTO.order());
        DataTableReader<String, Order> orders = orderService.getDataTableReader();
        reservationService.makeReservation(orders, reservationDTO, password);

        return Result.SUCCEED.translatedJsonString(language);
    }
}
