package com.lalaalal.coffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lalaalal.coffee.misc.DelegateGetter;
import com.lalaalal.coffee.model.order.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"id", "name", "time", "order"})
public class ReservationDTO {
    private final String name;
    private final OrderDTO order;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time;

    public static ReservationDTO convertFrom(DelegateGetter<String, OrderDTO> delegate, Reservation reservation) {
        if (reservation == null)
            return null;
        return new ReservationDTO(
                reservation.getName(),
                delegate.get(reservation.getOrderId()),
                reservation.getTime()
        );
    }

    @JsonCreator
    public ReservationDTO(
            @JsonProperty("name") String name,
            @JsonProperty("order") OrderDTO order,
            @JsonProperty("time") LocalDateTime time
    ) {
        this.name = name;
        this.order = order;
        this.time = time;
    }

    @JsonProperty("id")
    public String getId() {
        return order.getId();
    }

    public Reservation convertToReservation(String hashedPassword) {
        return new Reservation(
                this.getName(),
                hashedPassword,
                this.order.getId(),
                this.getTime()
        );
    }
}
