package com.lalaalal.coffee.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lalaalal.coffee.model.order.Modifier;
import com.lalaalal.coffee.model.order.argument.ArgumentCostModifier;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
public class Event {
    private final String title;
    private final String content;
    @JsonProperty("cost_modifiers")
    private Map<String, Modifier> costModifiers;
    @JsonProperty("argument_cost_modifier")
    private ArgumentCostModifier argumentCostModifier;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate start;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate end;

    @JsonCreator
    public Event(@JsonProperty("title") String title,
                 @JsonProperty("content") String content,
                 @JsonProperty("cost_modifiers") Map<String, Modifier> costModifiers,
                 @JsonProperty("argument_cost_modifier") ArgumentCostModifier argumentCostModifier,
                 @JsonProperty("start") LocalDate start,
                 @JsonProperty("end") LocalDate end) {
        this.title = title;
        this.content = content;
        this.costModifiers = costModifiers;
        this.argumentCostModifier = argumentCostModifier;
        this.start = start;
        this.end = end;
    }
}
