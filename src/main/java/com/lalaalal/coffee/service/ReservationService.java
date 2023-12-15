package com.lalaalal.coffee.service;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.DataTable;
import com.lalaalal.coffee.DateBasedKeyGenerator;
import com.lalaalal.coffee.misc.SHA256;
import com.lalaalal.coffee.model.order.Reservation;
import com.lalaalal.coffee.model.order.ReservationDTO;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final DataTable<String, Reservation> reservations;

    public ReservationService() {
        String saveFilePath = Configurations.getConfiguration("data.reservation.path");
        reservations = new DataTable<>(String.class, Reservation.class, new DateBasedKeyGenerator(), saveFilePath);
    }

    public void makeReservation(ReservationDTO reservation, String password) {
        String hashedPassword = SHA256.encrypt(password);
        reservations.add(Reservation.convertFrom(reservation, hashedPassword));
    }

    public void cancel(String id) {
        reservations.remove(id);
    }

    public ReservationDTO getReservation(String id) {
        return reservations.findById(id).convertTo();
    }
}
