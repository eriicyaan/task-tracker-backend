package com.handler.exception;


import com.exception.NotValidJwtTokenException;
import com.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }


    @ExceptionHandler(NotValidJwtTokenException.class)
    public ProblemDetail handleNotValidJwtTokenException(NotValidJwtTokenException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
    }







}
