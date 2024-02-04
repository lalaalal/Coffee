package com.lalaalal.coffee.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.model.Event;
import com.lalaalal.coffee.model.menu.Drink;
import com.lalaalal.coffee.model.menu.Menu;
import com.lalaalal.coffee.model.menu.Temperature;
import com.lalaalal.coffee.model.order.Modifier;
import lombok.Getter;

import java.util.Set;

@Getter
public class MenuDTO {
    private final String id;
    private final String name;
    private final int cost;
    @JsonProperty("required_arguments")
    private final Set<String> requiredArguments;
    @JsonProperty("available_temperature")
    private final Temperature[] availableTemperature;

    public MenuDTO(Menu menu, Event event, Language language) {
        this.id = menu.getId();
        this.name = language.translate(menu.getTranslationKey());
        Modifier modifier = event.getCostModifier(menu.getId());
        this.cost = modifier.apply(menu.getCost());
        requiredArguments = menu.getRequiredArgumentNames();
        if (menu instanceof Drink drink) {
            this.availableTemperature = drink.getAvailableTemperature();
        } else {
            this.availableTemperature = new Temperature[0];
        }
    }
}
