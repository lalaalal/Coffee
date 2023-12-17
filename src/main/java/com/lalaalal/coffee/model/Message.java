package com.lalaalal.coffee.model;

import com.lalaalal.coffee.Language;

public class Message {
    private final String key;
    private final Object[] args;

    public static Message of(String key, Object... args) {
        return new Message(key, args);
    }

    private Message(String key, Object[] args) {
        this.key = key;
        this.args = args;
    }

    public String translate(Language language) {
        String message = language.translate(key);
        return message.formatted(args);
    }

    @Override
    public String toString() {
        return key;
    }
}
