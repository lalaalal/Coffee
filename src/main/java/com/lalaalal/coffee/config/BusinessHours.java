package com.lalaalal.coffee.config;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.lalaalal.coffee.CoffeeApplication;
import com.lalaalal.coffee.initializer.Initialize;
import com.lalaalal.coffee.registry.Registries;
import com.lalaalal.coffee.registry.TimeRangeRegistry;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;

@Slf4j
public class BusinessHours {
    private static BusinessHours instance = null;

    private final Map<DayOfWeek, Map<String, TimeRange>> map = new HashMap<>();

    private BusinessHours() {
    }

    @Initialize(with = Registries.class)
    public static void initialize() throws IOException {
        BusinessHours instance = getInstance();

        String configFilePath = Configurations.getConfiguration("config.path.business_hours");
        try (InputStream inputStream = new FileInputStream(configFilePath)) {
            TypeFactory typeFactory = CoffeeApplication.MAPPER.getTypeFactory();
            Map<DayOfWeek, String[]> configure = CoffeeApplication.MAPPER.readValue(inputStream, typeFactory.constructMapType(Map.class, DayOfWeek.class, String[].class));
            for (DayOfWeek dayOfWeek : configure.keySet()) {
                String[] value = configure.get(dayOfWeek);
                log.debug("Adding {}: {}", dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH), value);
                instance.map.put(dayOfWeek, DateBusinessHours.of(value));
            }
        } catch (IOException exception) {
            log.error("Cannot initialize business hours.");
            throw exception;
        }
    }

    public static BusinessHours getInstance() {
        if (instance == null)
            return instance = new BusinessHours();
        return instance;
    }

    public List<LocalTime> getAvailableReservationTimes(DayOfWeek dayOfWeek) {
        ArrayList<LocalTime> times = new ArrayList<>();

        LocalTime time = getOpenTime(dayOfWeek);
        if (time == null)
            return times;
        final LocalTime closeTime = getCloseTime(dayOfWeek);
        while (time.isBefore(closeTime)) {
            if (isOpenAt(dayOfWeek, time))
                times.add(time);

            time = time.plusMinutes(30);
        }

        return times;
    }

    public LocalTime getOpenTime(DayOfWeek dayOfWeek) {
        TimeRange openTimeRange = map.getOrDefault(dayOfWeek, Map.of()).get(TimeRange.ID_OPEN);

        if (openTimeRange == null)
            return null;
        return openTimeRange.getFrom();
    }


    public LocalTime getCloseTime(DayOfWeek dayOfWeek) {
        TimeRange openTimeRange = map.getOrDefault(dayOfWeek, Map.of()).get(TimeRange.ID_OPEN);

        if (openTimeRange == null)
            return null;
        return openTimeRange.getTo();
    }

    public boolean isOpenAt(DayOfWeek dayOfWeek, LocalTime time) {
        TimeRange openTimeRange = map.getOrDefault(dayOfWeek, Map.of()).get(TimeRange.ID_OPEN);

        for (TimeRange range : map.getOrDefault(dayOfWeek, Map.of()).values()) {
            if (range.getType().equals(TimeRangeType.CLOSE) && range.isInRange(time))
                return false;
        }
        return openTimeRange.isInRange(time);
    }

    private static class DateBusinessHours extends HashMap<String, TimeRange> {
        public static DateBusinessHours of(String... keys) {
            DateBusinessHours map = new DateBusinessHours();
            for (String key : keys)
                map.put(key);

            return map;
        }

        public void put(String key) {
            String[] parts = key.split("\\.");
            if (parts.length > 1) {
                this.put(parts[0], Registries.get(TimeRangeRegistry.class, key));
                return;
            }
            this.put(Registries.get(TimeRangeRegistry.class, key));
        }

        public void put(TimeRange value) {
            this.put(value.getId(), value);
        }

        @Override
        public TimeRange get(Object key) {
            return super.get(key);
        }
    }
}
