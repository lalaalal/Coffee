package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.config.BusinessHours;
import com.lalaalal.coffee.dto.ReservationDTO;
import com.lalaalal.coffee.service.OrderService;
import com.lalaalal.coffee.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
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
    public String viewWeek(Model model, @RequestParam(value = "offset", defaultValue = "0") int offset) {
        LocalDate monday = LocalDate.now()
                .plusWeeks(offset)
                .with(DayOfWeek.MONDAY);
        Language language = getUserLanguage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<LocalDate> dates = new ArrayList<>();
        Map<LocalDate, String> dayOfWeeks = new HashMap<>();
        Map<LocalDate, List<ReservationDTO>> weekReservations = new HashMap<>();
        Collection<ReservationDTO> reservationDTOs = reservationService.collectDTO(orderService.delegateGetter(), currentUser().getPermission());
        for (DayOfWeek dayOfWeek = monday.getDayOfWeek(); dayOfWeek.compareTo(DayOfWeek.SATURDAY) < 0; dayOfWeek = dayOfWeek.plus(1)) {
            LocalDate currentDate = monday.with(dayOfWeek);
            List<ReservationDTO> reservations = reservationDTOs.stream()
                    .filter(reservationDTO -> currentDate.isEqual(reservationDTO.getTime().toLocalDate()))
                    .toList();
            weekReservations.put(currentDate, reservations);
            String dayOfWeekText = dayOfWeek.getDisplayName(TextStyle.SHORT, language.getLocale());
            dayOfWeeks.put(currentDate, "%d (%s)".formatted(currentDate.getDayOfMonth(), dayOfWeekText));
            dates.add(currentDate);
        }

        model.addAttribute("lastWeekText", language.translate("text.last_week"));
        model.addAttribute("nextWeekText", language.translate("text.next_week"));
        model.addAttribute("addText", language.translate("text.add"));
        model.addAttribute("offset", offset);
        model.addAttribute("currentMonth", monday.format(DateTimeFormatter.ofPattern("yyyy-MM")));
        model.addAttribute("dayOfWeeks", dayOfWeeks);
        model.addAttribute("dates", dates);
        model.addAttribute("weekReservations", weekReservations);
        model.addAttribute("formatter", formatter);

        return "/reservation/view";
    }

    @GetMapping("/make")
    public String makeReservation(Model model, @RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null)
            date = LocalDate.now();

        BusinessHours businessHours = BusinessHours.getInstance();
        List<LocalTime> availableTimes = businessHours.getAvailableReservationTimes(date.getDayOfWeek());

        LocalDate current = date;
        reservationService.collectDTO(orderService.delegateGetter(), currentUser().getPermission()).stream()
                .filter(reservationDTO -> current.isEqual(reservationDTO.getTime().toLocalDate()))
                .forEach(reservationDTO -> availableTimes.remove(reservationDTO.getTime().toLocalTime()));

        model.addAttribute("date", date);
        model.addAttribute("availableTimes", availableTimes);
        return "/reservation/make";
    }

    @GetMapping("/{reservationId}")
    public String viewReservation(Model model, @PathVariable("reservationId") String reservationId) {
        ReservationDTO reservation = reservationService.getReservation(orderService.delegateGetter(), reservationId, currentUser().getPermission());
        model.addAttribute("reservation", reservation);

        return "/reservation/reservation-view";
    }
}
