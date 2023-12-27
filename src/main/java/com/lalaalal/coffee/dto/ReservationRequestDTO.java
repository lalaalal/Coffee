package com.lalaalal.coffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.misc.SHA256;
import com.lalaalal.coffee.model.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationRequestDTO extends ReservationDTO {
    private final String hashedPassword;

    @JsonCreator
    public ReservationRequestDTO(
            @JsonProperty("name") String name,
            @JsonProperty("order") Order order,
            @JsonProperty("time") LocalDateTime time,
            @JsonProperty("password") String password
    ) {
        super(name, order, time);
        this.hashedPassword = SHA256.encrypt(password);
    }
}
