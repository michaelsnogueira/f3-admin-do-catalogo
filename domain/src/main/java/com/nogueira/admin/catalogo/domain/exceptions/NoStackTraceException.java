package com.nogueira.admin.catalogo.domain.exceptions;

public class NoStackTraceException extends RuntimeException{

    public NoStackTraceException(String message) {
        this(message, null);
    }

    public NoStackTraceException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
