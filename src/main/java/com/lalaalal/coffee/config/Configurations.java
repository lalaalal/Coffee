package com.lalaalal.coffee.config;

import com.lalaalal.coffee.initializer.Initialize.Time;
import com.lalaalal.coffee.initializer.Initializer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations extends Initializer {
    private static final Properties defaults = new Properties();
    private static final Properties configurations = new Properties();

    private Configurations() {
    }

    public static void initialize() {
        Initializer.initialize(Configurations.class, Time.Pre);
        try (InputStream inputStream = Configurations.class.getResourceAsStream("/config/default.properties")) {
            defaults.load(inputStream);
        } catch (IOException e) {
            // TODO: 12/3/23 handle exception
        }

        try (InputStream inputStream = new FileInputStream("config.properties")) {
            configurations.load(inputStream);
        } catch (FileNotFoundException ignored) {

        } catch (IOException e) {
            // TODO: 12/3/23 handle exception
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
