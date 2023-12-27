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

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReservationService {
    public static final String DATE_TIME_PATTERN = "RyyMMddHHmm";
    private final DataTable<String, Reservation> reservations;

    public ReservationService() {
        String saveFilePath = Configurations.getConfiguration("data.reservation.path");
        reservations = new DataTable<>(String.class, Reservation.class, new DateBasedKeyGenerator(DATE_TIME_PATTERN), saveFilePath);
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

    public Result makeReservation(ReservationDTO reservation, String hashedPassword) {
        reservations.add(createReservation(reservation, hashedPassword));

        return Result.SUCCEED;
    }

    public void cancel(String id) {
        reservations.remove(id);
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
