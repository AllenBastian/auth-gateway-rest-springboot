package com.learn.auth.exception;


import com.learn.auth.http.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.BindException;
import java.util.ArrayList;


/*controller Advice annotation has a global scope. exception handlers in this class
applies to all controllers globally*/
@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleConstraintViolationException(ConstraintViolationException exception){

        //converts set of errors received from exception into array to get the first error
        String errorMessage = new ArrayList<>(exception.getConstraintViolations()).getFirst().getMessage();
        ApiResponse<String> myResponse = new ApiResponse<>("constraint validation error",errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleMethodViolationException(MethodArgumentNotValidException exception){

        //converts set of errors received from exception into array to get the first error

        String errorMessage = exception.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        ApiResponse<String> myResponse = new ApiResponse<>("constraint validation error",errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);

    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUsernameNotFoundException(UsernameNotFoundException exception){

        //converts set of errors received from exception into array to get the first error
        String errorMessage = exception.getMessage();
        ApiResponse<String> myResponse = new ApiResponse<>("Username not found",errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);

    }





}