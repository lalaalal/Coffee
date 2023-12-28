package com.lalaalal.coffee.exception;

import com.lalaalal.coffee.model.Result;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    private final Result result;

    public GeneralException() {
        // TODO: 12/28/23 add translation
        this.result = createResult("error.general.message.default");
    }

    public GeneralException(Throwable cause) {
        super(cause);
        this.result = createResult("error.general.message.default");
    }

    public GeneralException(String messageKey, Object... args) {
        this.result = createResult(messageKey, args);
    }

    public GeneralException(Throwable cause, String messageKey, Object... args) {
        super(cause);
        this.result = createResult(messageKey, args);
    }

    protected final String verifyKeyNamespace(String key) {
        String namespace = getNamespace();
        if (key.startsWith(namespace))
            return key;
        return namespace + "." + key;
    }

    protected final Result createResult(String messageKey, Object... args) {
        messageKey = verifyKeyNamespace(messageKey);
        return _createResult(messageKey, args);
    }

    protected Result _createResult(String messageKey, Object... args) {
        return Result.error(messageKey, args);
    }

    protected String getNamespace() {
        return "error.general.message";
    }
}
