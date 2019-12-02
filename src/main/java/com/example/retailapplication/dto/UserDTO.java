package com.example.retailapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * DTO for storing User related request to be parsed into Response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer userId;

    @NotEmpty(message = "Username is empty!")
    @Size(min = 3, max = 15, message = "Username must be from 3 to 15 characters!")
    private String username;

    @NotEmpty(message = "Password is empty!")
    @Size(min = 3, max = 15, message = "Password must be from 3 to 15 characters!")
    private String password;

    @PositiveOrZero(message = "Balance must be zero or larger")
    private Double balance;

    private Boolean isLoggedIn;
}
