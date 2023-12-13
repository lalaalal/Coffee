package com.lalaalal.coffee.model.order;

import com.lalaalal.coffee.misc.SHA256;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Reservation {
    @Getter
    private String name;
    private String hashedPassword;
    @Getter
    private Order order;

    public static Reservation convertFrom(ReservationDTO reservationDTO, String hashedPassword) {
        return new Reservation(reservationDTO.name(), hashedPassword, reservationDTO.order());
    }

    private boolean checkPassword(String password) {
        return hashedPassword.equals(SHA256.encrypt(password));
    }

    public ReservationDTO convertTo() {
        return new ReservationDTO(name, order);
    }

}
