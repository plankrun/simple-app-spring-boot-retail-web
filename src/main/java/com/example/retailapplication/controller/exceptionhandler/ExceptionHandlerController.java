package com.example.retailapplication.controller.exceptionhandler;

import com.example.retailapplication.exception.InvalidAmountException;
import com.example.retailapplication.exception.ConflictException;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.exception.UserNotLoggedInException;
import com.example.retailapplication.util.Response;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

/**
 * This class is used to custom error message that's shown on an error
 */
@ControllerAdvice
public class ExceptionHandlerController {

    /**
     * Exception handler for DataNotFoundException
     * @param e exception to be handled
     * @return response
     */
    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody Response dataNotFoundException(final DataNotFoundException e) {
        return Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.NOT_FOUND.toString())
                .responseMessage(e.getMessage())
                .build();
    }

    /**
     * Exception handler for ConflictException
     * @param e exception to be handled
     * @return response
     */
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public @ResponseBody Response conflictException(final ConflictException e) {
        return Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.CONFLICT.toString())
                .responseMessage(e.getMessage())
                .build();
    }

    /**
     * Exception handler for InvalidAmountException
     * @param e exception to be handled
     * @return response
     */
    @ExceptionHandler(InvalidAmountException.class)
    @ResponseStatus(value = HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
    public @ResponseBody Response invalidAmountException(final InvalidAmountException e) {
        return Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.toString())
                .responseMessage(e.getMessage())
                .build();
    }

    /**
     * Exception handler for UserNotLoggedInException
     * @param e exception to be handled
     * @return response
     */
    @ExceptionHandler(UserNotLoggedInException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public @ResponseBody Response userNotLoggedInException(final UserNotLoggedInException e) {
        return Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.FORBIDDEN.toString())
                .responseMessage(e.getMessage())
                .build();
    }

    /**
     * Exception handler for JsonParseException
     * @param e exception to be handled
     * @return response
     */
    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody Response jsonParseException(final JsonParseException e) {
        return Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.BAD_REQUEST.toString())
                .responseMessage(e.getMessage())
                .build();
    }

    /**
     * Exception handler for JsonMappingException
     * @param e exception to be handled
     * @return response
     */
    @ExceptionHandler(JsonMappingException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody Response jsonMappingException(final JsonMappingException e) {
        return Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.BAD_REQUEST.toString())
                .responseMessage(e.getMessage())
                .build();
    }

    /**
     * Exception handler for IllegalArgumentException
     * @param e exception to be handled
     * @return response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody Response illegalArgumentException(final IllegalArgumentException e) {
        return Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.BAD_REQUEST.toString())
                .responseMessage(e.getMessage())
                .build();
    }
}
