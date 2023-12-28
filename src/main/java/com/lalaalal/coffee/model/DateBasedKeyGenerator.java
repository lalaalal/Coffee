package com.lalaalal.coffee.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class DateBasedKeyGenerator extends KeyGenerator<String> {
    private static final String DATE_PATTERN = "yyMMdd";
    private static final String KEY_FORMAT = "%s-%03d";

    private final String datePattern;
    private final String keyFormat;
    private final boolean useAutoincrement;

    public DateBasedKeyGenerator() {
        this(DATE_PATTERN, KEY_FORMAT);
    }

    public DateBasedKeyGenerator(String datePattern, String keyFormat) {
        this.datePattern = datePattern;
        this.keyFormat = keyFormat;
        useAutoincrement = keyFormat.matches("^.*%s.*-.*%.*d.*");
    }

    @Override
    public String generateKey() {
        String keyFront = LocalDateTime.now().format(DateTimeFormatter.ofPattern(datePattern));

        if (useAutoincrement)
            return keyFormat.formatted(keyFront, findMaxTailId(keyFront) + 1);
        return keyFormat.formatted(keyFormat.formatted(keyFront));
    }

    private int findMaxTailId(String keyFront) {
        // TODO: 12/15/23 using cache
        String maxId = getKeySet()
                .stream()
                .filter(s -> s.contains(keyFront))
                .max(Comparator.naturalOrder())
                .orElse("-000");
        return Integer.parseInt(maxId.split("-")[1]);
    }
}
