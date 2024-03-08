package com.lalaalal.coffee;

import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.exception.FatalError;
import org.junit.jupiter.api.Test;

class ConfigurationsTest {

    @Test
    void initialize() throws FatalError {
        Configurations.initialize();
    }
}