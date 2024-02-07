package com.lalaalal.coffee.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lalaalal.coffee.misc.SHA256;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"id", "name", "contact", "password", "time", "order"})
public class ReservationRequestDTO extends ReservationDTO {
    @JsonIgnore
    private final String hashedPassword;

    @JsonCreator
    public ReservationRequestDTO(
            @JsonProperty("name") String name,
            @JsonProperty("contact") String contact,
            @JsonProperty("order") OrderDTO order,
            @JsonProperty("time") LocalDateTime time,
            @JsonProperty("password") String password,
            @JsonProperty("message") String message,
            @JsonProperty(value = "for_meeting", defaultValue = "true") boolean forMeeting
    ) {
        super(name, contact, order, time, message, forMeeting);
        this.hashedPassword = SHA256.encrypt(password);
    }
}
