package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.config.TimeRange;

public class TimeRangeRegistry extends Registry<TimeRange> {
    @Override
    public void initialize() {
        register(TimeRange.EMPTY);

        String filePath = Configurations.getConfiguration("config.path.time_range");
        loadListFromJson(filePath, TimeRange.class, this::register);
    }

    public void register(TimeRange value) {
        register(value.getId(), value);
    }

    @Override
    public TimeRange get(String key) {
        if (registry.containsKey(key))
            return super.get(key);
        return TimeRange.EMPTY;
    }
}
