package com.example.retailapplication.controller.rest;

import com.example.retailapplication.dto.UserDTO;
import com.example.retailapplication.dto.form.EditProfileForm;
import com.example.retailapplication.exception.ConflictException;
import com.example.retailapplication.exception.RetailApplicationException;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is used to receive user related requests
 */
@RestController
@RequestMapping("/rest/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * To login into the application
     * @param userDTO to store request
     * @return response that store data in the form of user DTO
     * @throws RetailApplicationException with the following detail:
         * @throws DataNotFoundException when user does not exist in database
         * @throws ConflictException when user already logged in before
     */
    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO userDTO) throws RetailApplicationException {
        return userService.login(userDTO.getUsername(), userDTO.getPassword());
    }

    /**
     * To register new user
     * @param userDTO to store request
     * @return response that store data in the form of user DTO
     * @throws RetailApplicationException with the following detail:
         * @throws ConflictException when user has been registered
         * @throws DataNotFoundException when user not found
     */
    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO userDTO) throws RetailApplicationException {
        return userService.register(userDTO.getUsername(), userDTO.getPassword());
    }

    /**
     * To get all user data
     * @return response that store data in the form of user DTO list
     * @throws DataNotFoundException when data is empty
     */
    @GetMapping("/list")
    public List<UserDTO> list() throws DataNotFoundException {
        return userService.list();
    }

    @PostMapping("/delete")
    public void delete(@RequestBody Integer userId) throws DataNotFoundException {
        userService.delete(userId);
    }

    @PostMapping("/edit")
    public UserDTO edit(@RequestBody EditProfileForm editProfileRequest) throws RetailApplicationException{
        return userService.editProfile(editProfileRequest.getUserId(), editProfileRequest.getPassword(), editProfileRequest.getNewUsername(), editProfileRequest.getNewPassword());
    }

    @PostMapping("/update")
    public UserDTO update(@RequestBody UserDTO userDTO) throws RetailApplicationException {
        return userService.update(userDTO.getUserId(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getBalance(), userDTO.getIsLoggedIn());
    }

    @PostMapping("/logout")
    public UserDTO logout(@RequestBody UserDTO userDTO) throws DataNotFoundException {
        return userService.logout(userDTO.getUserId());
    }

    public UserDTO showUserDetail(@RequestBody Integer userId) {
        return userService.showUserDetail(userId);
    }
}
