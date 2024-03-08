package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.misc.SHA256;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Reservation {
    private final String name;
    private final String contact;
    @JsonProperty("hashed_password")
    private final String hashedPassword;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time;
    @Setter
    @JsonProperty("order_id")
    private String orderId;
    @Getter @Setter
    private String message;
    @Setter
    @JsonProperty("for_meeting")
    private boolean forMeeting;

    @JsonCreator
    public Reservation(
            @JsonProperty("name") String name,
            @JsonProperty("contact") String contact,
            @JsonProperty("hashed_password") String hashedPassword,
            @JsonProperty("order_id") String orderId,
            @JsonProperty("time") LocalDateTime time,
            @JsonProperty("message") String message,
            @JsonProperty(value = "for_meeting", defaultValue = "true") boolean forMeeting) {
        this.name = name;
        this.contact = contact;
        this.hashedPassword = hashedPassword;
        this.orderId = orderId;
        this.time = time;
        this.message = message;
        this.forMeeting = forMeeting;
    }

    @JsonIgnore
    public String getId() {
        return orderId;
    }

    public boolean checkPassword(String password) {
        return hashedPassword.equals(SHA256.encrypt(password));
    }
}
