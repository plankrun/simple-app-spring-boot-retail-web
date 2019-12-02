package com.example.retailapplication.service;

import com.example.retailapplication.dto.UserDTO;
import com.example.retailapplication.entity.User;
import com.example.retailapplication.exception.ConflictException;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.exception.InvalidAmountException;
import com.example.retailapplication.exception.RetailApplicationException;
import com.example.retailapplication.util.mapper.UserMapper;
import com.example.retailapplication.repository.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class is used to process user related request from UserController
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private static final String USER_NOT_FOUND = "User not found!";

    /**
     * To login into the application
     * @param username to store username from request
     * @param password to store password from request
     * @return response that store data in the form of user DTO
     * @throws RetailApplicationException with the following detail:
     *      @throws DataNotFoundException when user does not exist in database
     *      @throws ConflictException when user already logged in before
     */
    public UserDTO login(String username, String password) throws RetailApplicationException {
        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user == null) {
            throw new DataNotFoundException(USER_NOT_FOUND);
        }

        if (user.getIsLoggedIn()) {
            throw new ConflictException("Already logged in!");
        }

        user.setIsLoggedIn(true);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    /**
     * To register new user
     * @param username to store username from request
     * @param password to store password from request
     * @return response that store data in the form of user DTO
     * @throws RetailApplicationException with the following detail:
         * @throws ConflictException when user has been registered
         * @throws DataNotFoundException when user not found
     */
    public UserDTO register(String username, String password) throws RetailApplicationException {
        if (username.isEmpty() || password.isEmpty()) {
            throw new DataNotFoundException("Please fill all required data!");
        }

        if(userRepository.findByUsername(username) != null){
            throw new ConflictException("User with that username already exist!");
        }

        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .password(password)
                .balance(0.00)
                .isLoggedIn(false)
                .build();

        User user = userMapper.userDtoToUser(userDTO);
        userRepository.save(user);
        userDTO.setUserId(user.getUserId());

        return userDTO;
    }

    /**
     * To get all user data
     * @return response that store data in the form of user DTO
     * @throws DataNotFoundException when data is empty
     */
    public List<UserDTO> list() throws DataNotFoundException {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = userMapper.userListToUserDtoList(userList);

        if (userDTOList.isEmpty()) {
            throw new DataNotFoundException(USER_NOT_FOUND);
        }

        return userDTOList;
    }

    public void delete(Integer userId) throws DataNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) {
            throw new DataNotFoundException(USER_NOT_FOUND);
        }

        userRepository.deleteById(user.get().getUserId());
    }

    public UserDTO update(Integer userId, String username, String password, Double balance, Boolean isLoggedIn) throws RetailApplicationException {
        if (userId == null || username.isEmpty() || password.isEmpty() || balance == null) {
            throw new DataNotFoundException("Please fill all required data!");
        } else if (balance < 0) {
            throw new InvalidAmountException("Invalid balance amount!");
        }

        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) {
            throw new DataNotFoundException(USER_NOT_FOUND);
        }

        User currentUser = user.get();
        User findUser = userRepository.findByUsername(username);

        // If username is different and userId is also different
        if((findUser != null) && !(findUser.getUserId().equals(currentUser.getUserId()))) {
            throw new ConflictException("User with that username already exist!");
        }

        UserDTO userDTO = UserDTO.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .balance(balance)
                .isLoggedIn(isLoggedIn)
                .build();

        userRepository.save(userMapper.userDtoToUser(userDTO));

        return userDTO;
    }

    public UserDTO editProfile(Integer userId, String password, String newUsername, String newPassword) throws RetailApplicationException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new DataNotFoundException(USER_NOT_FOUND);
        }
        User user = optionalUser.get();

        if (!password.equals(user.getPassword())) {
            throw new ConflictException("Old password is wrong!");
        }

        if (userRepository.findByUsername(newUsername) != null) {
            throw new ConflictException("Username already taken!");
        }

        user.setUsername(newUsername);
        user.setPassword(newPassword);
        userRepository.save(user);

        return userMapper.userToUserDto(user);
    }

    public UserDTO logout(Integer userId) throws DataNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new DataNotFoundException(USER_NOT_FOUND);
        }
        User user = optionalUser.get();

        user.setIsLoggedIn(false);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    public UserDTO showUserDetail(Integer userId) {
        User user = userRepository.findById(userId).get();
        return userMapper.userToUserDto(user);
    }
}
