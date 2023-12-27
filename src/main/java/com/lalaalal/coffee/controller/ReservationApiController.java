package com.lalaalal.coffee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.dto.ReservationRequestDTO;
import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.service.OrderService;
import com.lalaalal.coffee.service.ReservationService;
import com.lalaalal.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservation")
@SuppressWarnings("unused")
public class ReservationApiController extends BaseController {
    private final ObjectMapper mapper = CoffeeApplication.MAPPER;
    private final ReservationService reservationService;
    private final OrderService orderService;

    @Autowired
    public ReservationApiController(ReservationService reservationService, OrderService orderService, UserService userService) {
        super(userService);
        this.reservationService = reservationService;
        this.orderService = orderService;
    }

    @PostMapping("/take")
    public ResponseEntity<ResultDTO> takeReservation(@RequestBody ReservationRequestDTO reservation) {
        orderService.addOrder(reservation.getOrder());
        Result result = reservationService.makeReservation(reservation, reservation.getHashedPassword());

        return createResultEntity(result);
    }
}
