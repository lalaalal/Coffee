package com.lalaalal.coffee.controller;

import com.lalaalal.coffee.Language;
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
import java.time.LocalDateTime;
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
    public String selectDate(Model model, @RequestParam(value = "offset", defaultValue = "0") int offset) {
        LocalDate monday = LocalDate.now()
                .plusWeeks(offset)
                .with(DayOfWeek.MONDAY);
        Language language = getUserLanguage();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        List<LocalDate> dates = new ArrayList<>();
        Map<LocalDate, String> dayOfWeeks = new HashMap<>();
        Map<LocalDate, List<ReservationDTO>> weekReservations = new HashMap<>();
        Collection<ReservationDTO> reservationDTOs = reservationService.collectDTO(orderService.delegateGetter());
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

        // TODO : make read from file

        List<LocalDateTime> availableTimes = new ArrayList<>();
        availableTimes.add(date.atTime(9, 30));
        availableTimes.add(date.atTime(10, 0));
        availableTimes.add(date.atTime(10, 30));
        availableTimes.add(date.atTime(11, 30));
        availableTimes.add(date.atTime(12, 0));
        availableTimes.add(date.atTime(12, 30));

        model.addAttribute("availableTimes", availableTimes);
        return "/reservation/make";
    }
}
