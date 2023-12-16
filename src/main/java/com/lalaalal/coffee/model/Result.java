package com.lalaalal.coffee.model;

import com.lalaalal.coffee.Language;

public record Result(int code, String messageKey) {
    public static final int SUCCEED_CODE = 0x10;
    public static final int FAILED_CODE = 0xf0;
    public static final Result SUCCEED = new Result(200, "");

    public static Result succeed(String messageKey) {
        return new Result(SUCCEED_CODE, messageKey);
    }

    public static Result failed(String messageKey) {
        return new Result(FAILED_CODE, messageKey);
    }

    @Override
    public String toString() {
        String format = "{\"code\": %d, \"messageKey\": \"%s\"}";
        return format.formatted(code, messageKey);
    }

    public String translatedJsonString(Language language) {
        String format = "{\"code\": %d, \"messageKey\": \"%s\"}";
        return format.formatted(code, language.translate(messageKey));

    }
}
