package com.lalaalal.coffee.exception;

import com.lalaalal.coffee.model.Result;

public class ClientCausedException extends GeneralException {
    public ClientCausedException() {
    }

    public ClientCausedException(Throwable cause) {
        super(cause);
    }

    public ClientCausedException(String messageKey, Object... args) {
        super(messageKey, args);
    }

    public ClientCausedException(Throwable cause, String messageKey, Object... args) {
        super(cause, messageKey, args);
    }

    @Override
    protected Result _createResult(String messageKey, Object... args) {
        return Result.failed(messageKey, args);
    }

    @Override
    protected String getNamespace() {
        return "error.client.message";
    }
}
