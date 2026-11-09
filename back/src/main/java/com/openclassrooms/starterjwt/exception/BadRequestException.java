package com.openclassrooms.starterjwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BadRequestException extends RuntimeException {
    private String message;
}
