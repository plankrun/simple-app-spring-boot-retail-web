package com.example.retailapplication.dto.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for storing data in Edit Profile Form
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileForm {
    private Integer userId;
    private String password;
    private String newUsername;
    private String newPassword;
}