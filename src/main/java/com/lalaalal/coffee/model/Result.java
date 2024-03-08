package com.lalaalal.coffee.model;

import com.lalaalal.coffee.Language;
import com.lalaalal.coffee.dto.ResultDTO;
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

    public static Result forbidden(String messageKey, Object... args) {
        return new Result(HttpStatus.FORBIDDEN, Message.of(messageKey, args));
    }

    public static Result error(String messageKey, Object... args) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR, messageKey, args);
    }

    public static Result error(Message message) {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static Result of(HttpStatus status, String messageKey, Object... args) {
        return new Result(status, Message.of(messageKey, args));
    }

    @Override
    public String toString() {
        String format = "{\"status\": %d, \"messageKey\": \"%s\"}";
        return format.formatted(status.value(), message);
    }

    public String getTranslatedMessage(Language language) {
        return message.translate(language);
    }

    public ResultDTO toResultDTO(Language language) {
        return new ResultDTO(status.value(), getTranslatedMessage(language));
    }

    @Deprecated
    public String translatedJsonString(Language language) {
        String format = "{\"status\": %d, \"messageKey\": \"%s\"}";
        return format.formatted(status.value(), message.translate(language));
    }
}
