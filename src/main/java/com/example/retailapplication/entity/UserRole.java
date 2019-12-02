package com.example.retailapplication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This is a Javadoc comment
 */
@Getter
@Setter
@Entity
public class UserRole implements Serializable {

    @Id
    private Long userId;

    @Id
    private Long roleId;
}