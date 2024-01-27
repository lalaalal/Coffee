package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.dto.ReservationDTO;
import com.lalaalal.coffee.service.OrderService;
import com.lalaalal.coffee.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/reservation")
public class ReservationController extends SessionHelper {
    private final OrderService orderService;
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(HttpSession httpSession, OrderService orderService, ReservationService reservationService) {
        super(httpSession);
        this.orderService = orderService;
        this.reservationService = reservationService;
    }

    @GetMapping("/view")
    public String selectDate(Model model, @RequestParam(value = "offset", defaultValue = "0") int offset) {
        LocalDate monday = LocalDate.now()
                .plusWeeks(offset)
                .with(DayOfWeek.MONDAY);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<DayOfWeek> dayOfWeeks = new ArrayList<>();
        Map<DayOfWeek, LocalDate> dates = new HashMap<>();
        Map<DayOfWeek, List<ReservationDTO>> weekReservations = new HashMap<>();
        Collection<ReservationDTO> reservationDTOs = reservationService.collectDTO(orderService.delegateGetter());
        for (DayOfWeek dayOfWeek = monday.getDayOfWeek(); dayOfWeek.compareTo(DayOfWeek.SATURDAY) < 0; dayOfWeek = dayOfWeek.plus(1)) {
            LocalDate currentDate = monday.with(dayOfWeek);
            List<ReservationDTO> reservations = reservationDTOs.stream()
                    .filter(reservationDTO -> currentDate.isEqual(reservationDTO.getTime().toLocalDate()))
                    .toList();
            weekReservations.put(dayOfWeek, reservations);
            dayOfWeeks.add(dayOfWeek);
            dates.put(dayOfWeek, currentDate);
        }
        model.addAttribute("dayOfWeeks", dayOfWeeks);
        model.addAttribute("dates", dates);
        model.addAttribute("weekReservations", weekReservations);
        model.addAttribute("formatter", formatter);

        return "reservation/view";
    }

    @GetMapping("/make")
    public String makeReservation(Model model) {
        return "reservation/make";
    }
}
