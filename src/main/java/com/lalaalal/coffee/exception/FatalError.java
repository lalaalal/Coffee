package com.lalaalal.coffee.exception;

public class FatalError extends Exception {
    public FatalError() {
    }

    public FatalError(String message) {
        super(message);
    }

    public FatalError(String message, Throwable cause) {
        super(message, cause);
    }

    public FatalError(Throwable cause) {
        super(cause);
    }
}
