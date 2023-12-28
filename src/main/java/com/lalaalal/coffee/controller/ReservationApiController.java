package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.dto.ReservationDTO;
import com.lalaalal.coffee.dto.ReservationRequestDTO;
import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.service.OrderService;
import com.lalaalal.coffee.service.ReservationService;
import com.lalaalal.coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/reservation")
@SuppressWarnings("unused")
public class ReservationApiController extends BaseController {
    private final ReservationService reservationService;
    private final OrderService orderService;

    @Autowired
    public ReservationApiController(ReservationService reservationService, OrderService orderService, UserService userService) {
        super(userService);
        this.reservationService = reservationService;
        this.orderService = orderService;
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Collection<ReservationDTO>> listOrder() {
        return createResponseEntity(
                reservationService.collectDTO(orderService.getDataTableReader()),
                HttpStatus.OK
        );
    }

    @PostMapping("/take")
    public ResponseEntity<ResultDTO> takeReservation(@RequestBody ReservationRequestDTO reservation) {
        String reservationId = reservationService.createReservationId(reservation);
        Result result = reservationService.makeReservation(reservation, reservation.getHashedPassword());
        if (!result.status().is2xxSuccessful())
            return createResultEntity(result);
        Order order = reservation.getOrder();
        order.setId(reservationId);
        Result orderResult = orderService.addOrder(order.getId(), order);

        return createResultEntity(orderResult);
    }

    @PostMapping("{reservationId}/cancel")
    public ResponseEntity<ResultDTO> cancelReservation(@PathVariable("reservationId") String reservationId) {
        ReservationDTO reservationDTO = reservationService.getReservation(orderService.getDataTableReader(), reservationId);
        Result orderResult = orderService.cancelOrder(reservationDTO.getOrder().getId());
        if (orderResult.status().is4xxClientError())
            return createResultEntity(orderResult);
        Result result = reservationService.cancel(reservationId);

        return createResultEntity(result);
    }
}
