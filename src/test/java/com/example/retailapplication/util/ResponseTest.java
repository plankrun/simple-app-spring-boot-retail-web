package com.example.retailapplication.util;

import com.example.retailapplication.controller.exceptionhandler.ExceptionHandlerController;
import com.example.retailapplication.controller.rest.UserController;
import com.example.retailapplication.dto.UserDTO;
import com.example.retailapplication.exception.DataNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ResponseTest {

    @Autowired
    UserController userController;

    @Test
    public void responseTest() {
        //Expected
        Response responseExpected = Response.<List<UserDTO>>builder()
                .timestamp(LocalDate.now())
                .responseCode(HttpStatus.NOT_FOUND.toString())
                .responseMessage("Error message")
                .build();

        //Actual
        Response responseActual = new ExceptionHandlerController().dataNotFoundException(new DataNotFoundException("Error message"));

        //Assertion
        assertEquals(responseExpected.getResponseMessage(), responseActual.getResponseMessage());
        assertEquals(responseExpected.getResponseCode(), responseActual.getResponseCode());
        assertEquals(responseExpected.getTimestamp(), responseActual.getTimestamp());
    }
}