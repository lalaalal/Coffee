package com.lalaalal.coffee.config;

import com.lalaalal.coffee.exception.FatalError;
import com.lalaalal.coffee.initializer.Initialize.Time;
import com.lalaalal.coffee.initializer.Initializer;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class Configurations implements Initializer {
    private static final Properties defaults = new Properties();
    private static final Properties configurations = new Properties();

    private Configurations() {
    }

    public static void initialize() throws FatalError {
        log.info("Initializing Configurations");
        Initializer.initialize(Configurations.class, Time.Pre);
        try (InputStream inputStream = Configurations.class.getResourceAsStream("/config/default.properties")) {
            defaults.load(inputStream);
        } catch (IOException exception) {
            log.error("Something went wrong while loading default configurations");
            throw new FatalError("Initializing configurations failed.", exception);
        }

        String userConfigFile = "./config/config.properties";
        try (InputStream inputStream = new FileInputStream(userConfigFile)) {
            configurations.load(inputStream);
        } catch (FileNotFoundException exception) {
            log.warn("Configuration file {} is not found", userConfigFile);
            log.warn("Pass loading target configurations.");
        } catch (IOException exception) {
            log.error("Something went wrong while loading user configurations");
            throw new FatalError("Initializing configurations failed.", exception);
        }
        Initializer.initialize(Configurations.class, Time.Post);
    }

    public static String getConfiguration(String key) {
        if (configurations.containsKey(key))
            return configurations.getProperty(key);
        if (defaults.containsKey(key))
            return defaults.getProperty(key);
        throw new IllegalArgumentException("Configuration key '%s' not found.".formatted(key));
    }

    public static int getIntConfiguration(String key) {
        String value = getConfiguration(key);

        return Integer.parseInt(value);
    }

    public static void setConfiguration(String key, String value) {
        configurations.setProperty(key, value);
    }

    public static String getDefaultConfiguration(String key) {
        return defaults.getProperty(key);
    }
}
