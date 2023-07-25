package me.dio.service.exception;

import java.io.Serial;

public class BusinessException extends RuntimeException {
    // best practice
    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
}
