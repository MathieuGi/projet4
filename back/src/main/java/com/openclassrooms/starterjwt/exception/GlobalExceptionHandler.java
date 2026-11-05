package com.openclassrooms.starterjwt.exception;

import com.openclassrooms.starterjwt.dto.ErrorResponse;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {
    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse entityExistsExcetionHandler(EntityExistsException e){
        return new ErrorResponse("ENTITY_EXISTS", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse argumentNotValidHandler(MethodArgumentNotValidException e){
        String message = "Invalid arguments";
        if(e.getFieldError() != null){
            message = e.getFieldError().getDefaultMessage();
        }
        return new ErrorResponse("INVALID_ARGUMENTS", message);
    }
}
