package com.lalaalal.coffee.model.order;

import java.time.LocalDateTime;

public record ReservationDTO(String name, Order order, LocalDateTime time) {

}
