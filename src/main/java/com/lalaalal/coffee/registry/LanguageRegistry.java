package com.lalaalal.coffee.registry;

import com.lalaalal.coffee.Configurations;
import com.lalaalal.coffee.Language;

import java.util.Locale;

public class LanguageRegistry extends Registry<Language> {
    @Override
    public void initialize() {
        register(new Language("ko", Locale.KOREAN));
        register(new Language("en", Locale.ENGLISH));
    }

    public void register(Language language) {
        register(language.getLanguage(), language);
    }

    @Override
    public Language get(String key) {
        if (registry.containsKey(key))
            return super.get(key);
        return super.get("en");
    }

    public Language getDefault() {
        String defaultLanguage = Configurations.getConfiguration("language.default");
        return get(defaultLanguage);
    }
}
