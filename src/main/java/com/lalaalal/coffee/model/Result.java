package com.lalaalal.coffee.model;

import com.lalaalal.coffee.Language;
import org.springframework.http.HttpStatus;

public record Result(HttpStatus status, Message message) {
    public static final Result SUCCEED = succeed("result.message.succeed");

    public static Result succeed(String messageKey, Object... args) {
        return new Result(HttpStatus.OK, Message.of(messageKey, args));
    }

    public static Result failed(String messageKey, Object... args) {
        return new Result(HttpStatus.BAD_REQUEST, Message.of(messageKey, args));
    }

    public static Result forbidden(String userName) {
        return new Result(HttpStatus.FORBIDDEN, Message.of("result.message.forbidden", userName));
    }

    @Override
    public String toString() {
        String format = "{\"status\": %d, \"messageKey\": \"%s\"}";
        return format.formatted(status.value(), message);
    }

    public String translatedJsonString(Language language) {
        String format = "{\"status\": %d, \"messageKey\": \"%s\"}";
        return format.formatted(status.value(), message.translate(language));
    }
}
