package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.dto.ReservationDTO;
import com.lalaalal.coffee.model.DataTable;
import com.lalaalal.coffee.model.DataTableReader;
import com.lalaalal.coffee.model.DateBasedKeyGenerator;
import com.lalaalal.coffee.model.Result;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.Reservation;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReservationService {
    public static final String DATE_TIME_PATTERN = "yyMMddHHmm";
    public static final String KEY_FORMAT = "R%s";
    private final DataTable<String, Reservation> reservations;

    public ReservationService() {
        String saveFilePath = Configurations.getConfiguration("data.reservation.path");
        reservations = new DataTable<>(String.class, Reservation.class, new DateBasedKeyGenerator(DATE_TIME_PATTERN, KEY_FORMAT), saveFilePath);
    }

    protected ReservationDTO convertToDTO(DataTableReader<String, Order> orders, Reservation reservation) {
        Order order = orders.get(reservation.getOrderId());
        return new ReservationDTO(
                reservation.getName(),
                order,
                reservation.getTime()
        );
    }

    protected Reservation createReservation(ReservationDTO reservationDTO, String hashedPassword) {
        return new Reservation(
                reservationDTO.getName(),
                hashedPassword,
                reservationDTO.getOrder().getId(),
                reservationDTO.getTime()
        );
    }

    public String createReservationId(ReservationDTO reservationDTO) {
        return reservationDTO.getTime().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
    }

    public Result makeReservation(ReservationDTO reservationDTO, String hashedPassword) {
        String reservationId = createReservationId(reservationDTO);
        if (reservations.containsKey(reservationId))
            return Result.failed("result.message.failed.reservation_exists_at", reservationId);

        Reservation reservation = createReservation(reservationDTO, hashedPassword);
        reservation.setOrderId(reservationId);
        reservations.add(reservationId, reservation);
        reservations.save();
        return Result.SUCCEED;
    }

    public Result cancel(String id) {
        if (!reservations.containsKey(id))
            return Result.failed("result.message.failed.no_such_reservation_id", id);
        reservations.remove(id);
        reservations.save();
        return Result.SUCCEED;
    }

    public ReservationDTO getReservation(DataTableReader<String, Order> orders, String id) {
        Reservation reservation = reservations.get(id);
        return convertToDTO(orders, reservation);
    }

    public Collection<ReservationDTO> collectDTO(DataTableReader<String, Order> orders) {
        ArrayList<ReservationDTO> list = new ArrayList<>();
        reservations.stream()
                .forEach(reservation -> list.add(convertToDTO(orders, reservation)));
        return list;
    }
}