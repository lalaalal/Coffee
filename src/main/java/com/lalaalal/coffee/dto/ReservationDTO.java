package com.lalaalal.coffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.model.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationDTO {
    private final String name;
    private final Order order;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time;

    @JsonCreator
    public ReservationDTO(
            @JsonProperty("name") String name,
            @JsonProperty("order") Order order,
            @JsonProperty("time") LocalDateTime time
    ) {
        this.name = name;
        this.order = order;
        this.time = time;
    }
}
