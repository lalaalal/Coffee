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
@JsonPropertyOrder({"id", "name", "time", "order", "comment", "for_meeting"})
public class ReservationDTO {
    private final String name;
    private final OrderDTO order;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time;
    // TODO : rename to message
    private final String message;
    @JsonProperty("for_meeting")
    private final boolean forMeeting;

    @JsonCreator
    public ReservationDTO(
            @JsonProperty("name") String name,
            @JsonProperty("order") OrderDTO order,
            @JsonProperty("time") LocalDateTime time,
            @JsonProperty("message") String message,
            @JsonProperty(value = "for_meeting", defaultValue = "true") boolean forMeeting
    ) {
        this.name = name;
        this.order = order;
        this.time = time;
        this.message = message;
        this.forMeeting = forMeeting;
    }

    public static ReservationDTO convertFrom(DelegateGetter<String, OrderDTO> delegate, Reservation reservation) {
        if (reservation == null)
            return null;
        return new ReservationDTO(
                reservation.getName(),
                delegate.get(reservation.getOrderId()),
                reservation.getTime(),
                reservation.getMessage(),
                reservation.isForMeeting()
        );
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
                this.getTime(),
                this.getMessage(),
                this.isForMeeting()
        );
    }
}
