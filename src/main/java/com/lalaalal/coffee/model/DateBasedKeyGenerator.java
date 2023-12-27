package com.lalaalal.coffee.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class DateBasedKeyGenerator extends KeyGenerator<String> {
    private static final String DATE_PATTERN = "yyMMdd";
    private static final String KEY_FORMAT = "%s-%03d";

    private final String datePattern;

    public DateBasedKeyGenerator() {
        this(DATE_PATTERN);
    }

    public DateBasedKeyGenerator(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public String generateKey() {
        String keyFront = LocalDateTime.now().format(DateTimeFormatter.ofPattern(datePattern));

        return KEY_FORMAT.formatted(keyFront, findMaxTailId(keyFront) + 1);
    }

    private int findMaxTailId(String keyFront) {
        // TODO: 12/15/23 using cache
        String maxId = getKeySet()
                .stream()
                .filter(s -> s.contains(keyFront))
                .max(Comparator.naturalOrder())
                .orElse("-001");
        return Integer.parseInt(maxId.split("-")[1]);
    }
}
