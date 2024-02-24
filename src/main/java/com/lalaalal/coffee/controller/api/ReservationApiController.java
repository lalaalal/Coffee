package com.lalaalal.coffee.controller.api;

import com.lalaalal.coffee.controller.SessionHelper;
import com.lalaalal.coffee.dto.OrderDTO;
import com.lalaalal.coffee.dto.ReservationDTO;
import com.lalaalal.coffee.dto.ReservationRequestDTO;
import com.lalaalal.coffee.dto.ResultDTO;
import com.lalaalal.coffee.exception.ClientCausedException;
import com.lalaalal.coffee.model.Event;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.service.EventService;
import com.lalaalal.coffee.service.OrderService;
import com.lalaalal.coffee.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/reservation")
public class ReservationApiController extends SessionHelper {
    private final ReservationService reservationService;
    private final OrderService orderService;
    private final EventService eventService;

    @Autowired
    public ReservationApiController(
            ReservationService reservationService,
            OrderService orderService,
            EventService eventService,
            HttpSession httpSession) {
        super(httpSession);
        this.reservationService = reservationService;
        this.orderService = orderService;
        this.eventService = eventService;
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Collection<ReservationDTO>> list() {
        return createResponseEntity(
                reservationService.collectDTO(orderService.delegateGetter(), currentUser().getPermission()),
                HttpStatus.OK
        );
    }

    @PostMapping("/make")
    public ResponseEntity<ResultDTO> make(@RequestBody ReservationRequestDTO reservation) {
        String reservationId = reservationService.createReservationId(reservation);
        Result result = reservationService.makeReservation(reservation, reservation.getHashedPassword());
        if (!result.status().is2xxSuccessful())
            return createResultEntity(result);
        OrderDTO order = reservation.getOrder();
        Event event = eventService.getEventAt(reservation.getTime().toLocalDate());
        Result orderResult = orderService.addOrder(reservationId, order, event);

        return createResultEntity(orderResult);
    }

    @RequestMapping(value = "/{reservationId}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<ReservationDTO> read(@PathVariable("reservationId") String reservationId) {
        if (!reservationService.isValidKey(reservationId))
            // TODO: 12/28/23 add translation
            throw new ClientCausedException("error.client.message.no_such_reservation_id", reservationId);
        ReservationDTO reservationDTO = reservationService.getReservation(orderService.delegateGetter(), reservationId, currentUser().getPermission());

        return createResponseEntity(reservationDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{reservationId}/cancel", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<ResultDTO> cancel(@PathVariable("reservationId") String reservationId) {
        ReservationDTO reservationDTO = reservationService.getReservation(orderService.delegateGetter(), reservationId, currentUser().getPermission());
        Result orderResult = orderService.cancelOrder(reservationDTO.getOrder().getId());
        if (orderResult.status().is4xxClientError())
            return createResultEntity(orderResult);
        Result result = reservationService.cancel(reservationId);

        return createResultEntity(result);
    }
}
