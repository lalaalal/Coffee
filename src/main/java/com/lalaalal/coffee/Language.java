package com.lalaalal.coffee;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {
    private static final String BASE_NAME = "lang";
    @Getter
    private final String language;
    @Getter
    private final Locale locale;
    private final ResourceBundle bundle;

    public Language(String language, Locale locale) {
        this.language = language;
        this.locale = locale;
        this.bundle = ResourceBundle.getBundle(BASE_NAME, locale);
    }

    public String translate(String key) {
        return bundle.getString(key);
    }
}
