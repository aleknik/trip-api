package com.aleknik.tripapi.controller.exception.resolver;

import com.aleknik.tripapi.controller.exception.NotFoundException;
import com.aleknik.tripapi.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Catches exceptions that occur in any layer of the application (domain, service, controller).
 * This allows us to not have to worry about exception handling, as they will be caught here.
 * It also allows us to simply throw a custom exception when necessary, instead of having to propagate it up.
 * <p>
 * Caught exceptions get mapped to an adequate {@link HttpStatus}, which is then returned to the client along with the
 * exception message.
 */
@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundException(NotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}

