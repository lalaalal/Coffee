package com.lalaalal.coffee.config;

import com.lalaalal.coffee.initializer.Initialize.Time;

import lombok.extern.slf4j.Slf4j;

import com.lalaalal.coffee.initializer.Initializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class Configurations extends Initializer {
    private static final Properties defaults = new Properties();
    private static final Properties configurations = new Properties();

    private Configurations() {
    }

    public static void initialize() {
        log.debug("Initializing Configurations");
        Initializer.initialize(Configurations.class, Time.Pre);
        try (InputStream inputStream = Configurations.class.getResourceAsStream("/config/default.properties")) {
            defaults.load(inputStream);
        } catch (IOException e) {
            log.error("Something went wrong while loading default configurations");
        }

        String userConfigFile = "./config/config.properties";
        try (InputStream inputStream = new FileInputStream(userConfigFile)) {
            configurations.load(inputStream);
        } catch (FileNotFoundException e) {
            log.warn("Configuation file %s".formatted(userConfigFile));
        } catch (IOException e) {
            log.error("Something went wrong while loading user configurations");
        }
        Initializer.initialize(Configurations.class, Time.Post);
    }

    public static String getConfiguration(String key) {
        if (configurations.containsKey(key))
            return configurations.getProperty(key);
        if (defaults.containsKey(key))
            return defaults.getProperty(key);
        throw new IllegalArgumentException();
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
