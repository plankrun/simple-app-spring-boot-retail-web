package com.example.retailapplication.controller;

import com.example.retailapplication.controller.exceptionhandler.ExceptionHandlerController;
import com.example.retailapplication.exception.ConflictException;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.exception.InvalidAmountException;
import com.example.retailapplication.exception.UserNotLoggedInException;
import com.example.retailapplication.util.Response;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class ExceptionHandlerControllerTest {

    @Test
    public void dataNotFoundException() {
        // Expected
        Response responseExpected = Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.NOT_FOUND.toString())
                .responseMessage("Error message")
                .build();

        // Actual
        Response responseActual = new ExceptionHandlerController().dataNotFoundException(new DataNotFoundException("Error message"));

        // Assertion
        assertEquals(responseExpected, responseActual);
    }

    @Test
    public void userConflictException() {
        // Expected
        Response responseExpected = Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.CONFLICT.toString())
                .responseMessage("Error message")
                .build();

        // Actual
        Response responseActual = new ExceptionHandlerController().conflictException(new ConflictException("Error message"));

        // Assertion
        assertEquals(responseExpected, responseActual);
    }

    @Test
    public void invalidAmountException() {
        // Expected
        Response responseExpected = Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.toString())
                .responseMessage("Error message")
                .build();

        // Actual
        Response responseActual = new ExceptionHandlerController().invalidAmountException(new InvalidAmountException("Error message"));

        // Assertion
        assertEquals(responseExpected, responseActual);
    }

    @Test
    public void userNotLoggedInException() {
        // Expected
        Response responseExpected = Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.FORBIDDEN.toString())
                .responseMessage("Error message")
                .build();

        // Actual
        Response responseActual = new ExceptionHandlerController().userNotLoggedInException(new UserNotLoggedInException("Error message"));

        // Assertion
        assertEquals(responseExpected, responseActual);
    }

    @Test
    public void jsonParseException() throws IOException {
        // Expected
        Response responseExpected = Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.BAD_REQUEST.toString())
                .responseMessage("\n" +
                        " at [Source: (String)\"\"; line: 1, column: 1]")
                .build();

        // Actual
        JsonParser jsonParser = new JsonFactory().createParser("");
        Response responseActual = new ExceptionHandlerController().jsonParseException(new JsonParseException(jsonParser, ""));

        // Assertion
        assertEquals(responseExpected, responseActual);
    }

    @Test
    public void jsonMappingException() throws IOException {
        // Expected
        Response responseExpected = Response.builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.BAD_REQUEST.toString())
                .responseMessage("\n" +
                        " at [Source: (String)\"\"; line: 1, column: 0]")
                .build();

        // Actual
        JsonParser jsonParser  = new JsonFactory().createParser("");
        Response responseActual = new ExceptionHandlerController().jsonMappingException(new JsonMappingException(jsonParser, ""));

        // Assertion
        assertEquals(responseExpected, responseActual);
    }
}