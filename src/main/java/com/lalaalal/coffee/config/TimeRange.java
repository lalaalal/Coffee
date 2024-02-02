package com.lalaalal.coffee.config;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class TimeRange {
    public static final String ID_OPEN = "open";
    public static final String ID_BREAK_TIME = "break_time";
    public static final String ID_LUNCH = "lunch";

    private final String id;
    private final TimeRangeType type;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime from;
    @JsonFormat(pattern = "HH:mm")
    private final LocalTime to;

    @JsonCreator
    public TimeRange(
            @JsonProperty("id") String id, 
            @JsonProperty("type") TimeRangeType type, 
            @JsonProperty("from") LocalTime from,
            @JsonProperty("to") LocalTime to) {
        this.id = id;
        this.type = type;
        this.from = from;
        this.to = to;
    }

    @JsonIgnore
    public String getTranslationId() {
        return "time_range." + id;
    }

    public boolean isInRange(LocalTime time) {
        return time.isAfter(from) && (time.isBefore(to) || time.equals(to));
    }
}