package com.lalaalal.coffee;

import com.lalaalal.coffee.config.Configurations;
import com.lalaalal.coffee.initializer.Initialize;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class Language {
    private static final String DEFAULT_BASE_NAME = "lang";
    private static final List<BundleCreator> BUNDLE_CREATORS = new ArrayList<>();

    @Getter
    private final String language;
    @Getter
    private final Locale locale;
    private final List<ResourceBundle> bundles;

    private static ResourceBundle getResourceBundle(String dirPath, String baseName, Locale locale) throws IOException {
        URL[] urls = {new File(dirPath).toURI().toURL()};
        try (URLClassLoader loader = new URLClassLoader(urls)) {
            return ResourceBundle.getBundle(baseName, locale, loader);
        }
    }

    @Initialize(with = Configurations.class)
    public static void initialize() {
        String[] paths = Configurations.getConfiguration("language.bundle.paths").split(":");
        String[] names = Configurations.getConfiguration("language.bundle.names").split(":");

        for (int index = 0; index < paths.length; index++) {
            String path = paths[index];
            String name = names[index];
            BUNDLE_CREATORS.add(new BundleCreator(path, name));
        }
    }

    public Language(String language, Locale locale) {
        this.language = language;
        this.locale = locale;
        this.bundles = new ArrayList<>();
        this.bundles.add(ResourceBundle.getBundle(DEFAULT_BASE_NAME, locale));
        for (BundleCreator creator : BUNDLE_CREATORS) {
            try {
                log.debug("Adding bundle '{}' to language '{}'", creator.baseName, language);
                ResourceBundle bundle = creator.create(locale);
                bundles.add(bundle);
            } catch (IOException exception) {
                log.warn("Cannot load translation data named '{}'", creator.baseName);
                log.warn(exception.getMessage());
            }
        }
    }

    public String translate(String key) {
        for (ResourceBundle bundle : bundles) {
            if (bundle.containsKey(key))
                return bundle.getString(key);
        }
        return key;
    }

    @Override
    public String toString() {
        return locale.getDisplayLanguage();
    }

    @Getter
    private static class BundleCreator {
        private final String directory;
        private final String baseName;

        private BundleCreator(String directory, String baseName) {
            this.directory = directory;
            this.baseName = baseName;
        }

        public ResourceBundle create(Locale locale) throws IOException {
            return getResourceBundle(directory, baseName, locale);
        }
    }
}
