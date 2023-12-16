package com.lalaalal.coffee.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.misc.SHA256;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private String name;
    @JsonProperty("hashed_password")
    private String hashedPassword;
    @JsonProperty("order_id")
    private String orderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    private boolean checkPassword(String password) {
        return hashedPassword.equals(SHA256.encrypt(password));
    }
}
