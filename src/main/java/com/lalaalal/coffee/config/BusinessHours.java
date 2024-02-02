package com.lalaalal.coffee.config;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.lalaalal.coffee.initializer.Initialize;
import com.lalaalal.coffee.registry.Registries;
import com.lalaalal.coffee.registry.TimeRangeRegistry;

public class BusinessHours {
    private static BusinessHours instance = null;

    private final Map<DayOfWeek, Map<String, TimeRange>> map = new HashMap<>();

    @Initialize(with = Registries.class)
    public static void initialize() {
        // TODO : load from file...
        BusinessHours instance = getInstance();
        
        DayOfWeek[] normal = { DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.THURSDAY };
        for (DayOfWeek dayOfWeek : normal) {
            instance.map.put(dayOfWeek, DateBusinessHours.of(
                TimeRange.ID_OPEN, TimeRange.ID_LUNCH, TimeRange.ID_BREAK_TIME
            ));
        }
        instance.map.put(DayOfWeek.WEDNESDAY, DateBusinessHours.of(
            "open.wednesday", TimeRange.ID_LUNCH, TimeRange.ID_BREAK_TIME
        ));
        instance.map.put(DayOfWeek.FRIDAY, DateBusinessHours.of(
            "open.friday", TimeRange.ID_LUNCH
        ));
    }

    public static BusinessHours getInstance() {
        if (instance == null)
            return instance = new BusinessHours();
        return instance;
    }

    private BusinessHours() { }

    public List<LocalTime> getAvailableReservationTimes(DayOfWeek dayOfWeek) {
        ArrayList<LocalTime> times = new ArrayList<>();
        
        LocalTime time = getOpenTime(dayOfWeek);
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

        public TimeRange put(String key) {
            String[] parts = key.split("\\.");
            if (parts.length > 1)
                return this.put(parts[0], Registries.get(TimeRangeRegistry.class, key));
            return this.put(Registries.get(TimeRangeRegistry.class, key));
        }

        public TimeRange put(TimeRange value) {
            return this.put(value.getId(), value);
        }

        @Override
        public TimeRange get(Object key) {
            return super.get(key);
        }
    }
}
