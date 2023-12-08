package com.lalaalal.coffee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Reservation {
    private String name;
    private String passwd;
    private Order order;
}
