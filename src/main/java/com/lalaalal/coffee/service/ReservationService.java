package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.DataTable;
import com.lalaalal.coffee.DataTableReader;
import com.lalaalal.coffee.DateBasedKeyGenerator;
import com.lalaalal.coffee.misc.SHA256;
import com.lalaalal.coffee.model.order.Order;
import com.lalaalal.coffee.model.order.Reservation;
import com.lalaalal.coffee.model.order.ReservationDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationService {
    private final DataTable<String, Reservation> reservations;

    public ReservationService() {
        String saveFilePath = Configurations.getConfiguration("data.reservation.path");
        reservations = new DataTable<>(String.class, Reservation.class, new DateBasedKeyGenerator(), saveFilePath);
    }

    protected ReservationDTO convertToDTO(DataTableReader<String, Order> orders, Reservation reservation) {
        Order order = orders.findById(reservation.getOrderId());
        return new ReservationDTO(
                reservation.getName(),
                order
        );
    }

    protected Reservation createReservation(ReservationDTO reservationDTO, String hashedPassword) {
        return new Reservation(
                reservationDTO.name(),
                hashedPassword,
                reservationDTO.order().getId(),
                LocalDateTime.now()
        );
    }

    public void makeReservation(DataTableReader<String, Order> orders, ReservationDTO reservation, String password) {
        String hashedPassword = SHA256.encrypt(password);
        reservations.add(createReservation(reservation, hashedPassword));
    }

    public void cancel(String id) {
        reservations.remove(id);
    }

    public ReservationDTO getReservation(DataTableReader<String, Order> orders, String id) {
        Reservation reservation = reservations.findById(id);
        return convertToDTO(orders, reservation);
    }
}
