package com.example.retailapplication.controller;

import com.example.retailapplication.controller.rest.UserController;
import com.example.retailapplication.dto.UserDTO;
import com.example.retailapplication.exception.ConflictException;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.exception.RetailApplicationException;
import com.example.retailapplication.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @Test
    public void login() throws RetailApplicationException {
        // Expected
        UserDTO expected = UserDTO.builder()
                .userId(1)
                .username("Apri")
                .password("apri")
                .balance(10000.00)
                .isLoggedIn(true)
                .build();

        // Actual
        UserDTO userDTO = UserDTO.builder()
                .username("Apri")
                .password("apri")
                .build();

        UserDTO actual = userController.login(userDTO);

        // Assertion
        assertEquals(expected, actual);
    }

    @Test
    public void register() throws RetailApplicationException {
        // Expected
        UserDTO expected =
UserDTO.builder()
                .userId(4)
                .username("Widianto")
                .password("widianto")
                .balance(0.00)
                .isLoggedIn(false)
                .build();
        // Actual
        UserDTO userDTO = UserDTO.builder()
                .username("Widianto")
                .password("widianto")
                .build();

        UserDTO actual = userController.register(userDTO);

        // Assertion
        assertEquals(expected, actual);
    }

    @Test
    public void getUserList() throws DataNotFoundException {
        // Assertion
        assertNotNull(userController.list());
    }

    /**
     * Start of exception test
     */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void userNotFoundError() throws RetailApplicationException {
        // Expected & Assertion
        expectedException.expect(DataNotFoundException.class);
        expectedException.expectMessage("User not found!");

        // Actual
        UserDTO userDTO = UserDTO.builder()
                .username("Satria")
                .password("satria")
                .build();

        userController.login(userDTO);
    }

    @Test
    public void userAlreadyLoggedInError() throws RetailApplicationException {
        // Expected & Assertion
        expectedException.expect(ConflictException.class);
        expectedException.expectMessage("Already logged in");

        // Actual
        UserDTO userDTO = UserDTO.builder()
                .username("Yanti")
                .password("yanti")
                .build();

        userController.login(userDTO);
    }

    @Test
    public void usernameAlreadyExistError() throws RetailApplicationException {
        // Expected & Assertion
        expectedException.expect(ConflictException.class);
        expectedException.expectMessage("User with that username already exist!");

        // Actual
        UserDTO userDTO = UserDTO.builder()
                .username("Apri")
                .password("apri")
                .build();

        userController.register(userDTO);
    }

    @Test
    public void userListNotFoundError() throws DataNotFoundException {
        // Expected & Assertion
        expectedException.expect(DataNotFoundException.class);
        expectedException.expectMessage("User not found!");

        // Actual
        userRepository.deleteAll();
        userController.list();
    }
}