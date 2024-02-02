package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.config.TimeRange;

public class TimeRangeRegistry extends Registry<TimeRange> {
    @Override
    public void initialize() {
        String filePath = Configurations.getConfiguration("time_range.path");
        loadListFromJson(filePath, TimeRange.class, this::register);
    }

    public void register(TimeRange value) {
        register(value.getId(), value);
    }
}
