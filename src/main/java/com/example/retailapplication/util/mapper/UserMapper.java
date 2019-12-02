package com.example.retailapplication.util.mapper;

import com.example.retailapplication.dto.UserDTO;
import com.example.retailapplication.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * This class is used to map User entity to User DTO and vice versa
 */
@Mapper
public interface UserMapper {

    /**
     * Map User entity into User DTO
     * @param user entity to be mapped
     * @return User DTO
     */
    UserDTO userToUserDto(User user);

    /**
     * Map User DTO into User entity
     * @param userDTO to be mapped
     * @return User entity
     */
    User userDtoToUser(UserDTO userDTO);

    /**
     * Map List<User> into List<UserDTO>
     * @param userList list of user entity to be mapped
     * @return list of User DTO
     */
    List<UserDTO> userListToUserDtoList(List<User> userList);
}
