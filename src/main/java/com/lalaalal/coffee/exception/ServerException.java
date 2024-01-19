package com.lalaalal.coffee.exception;

public class ServerException extends GeneralException {
    public ServerException() {
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public ServerException(String messageKey, Object... args) {
        super(messageKey, args);
    }

    public ServerException(Throwable cause, String messageKey, Object... args) {
        super(cause, messageKey, args);
    }

    @Override
    protected String getNamespace() {
        return "error.server.message";
    }
}
