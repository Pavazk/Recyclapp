package com.project.recyclapp.commons.exceptions;

import com.project.recyclapp.users.models.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response> customExceptionHandler(CustomException customException){
        return new ResponseEntity<>(
                Response.builder()
                        .date(LocalDate.now())
                        .message(List.of(customException.getMessage()))
                        .statusCode(HttpStatus.CONFLICT.name())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> validatorExceptionHandler(MethodArgumentNotValidException exception) {
        List<String> errorList = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(objectError ->
                errorList.add(
                        ((FieldError) objectError).getField() + "|" + objectError.getDefaultMessage()
                )
        );
        return new ResponseEntity<>(
                Response.builder()
                        .date(LocalDate.now())
                        .message(errorList)
                        .statusCode(HttpStatus.BAD_REQUEST.name())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}
