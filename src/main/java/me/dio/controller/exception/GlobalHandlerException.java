package me.dio.controller.exception;

import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalHandlerException.class);
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleUnexpectedException(Throwable exception) {
        String message = "Unexpected server error, see logs.";
        LOGGER.error(message, exception);
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
