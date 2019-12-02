package com.example.retailapplication.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * User entity to store user related data from and into database
 */
@Data
@Entity
public class User {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="userId_seq")
    @SequenceGenerator(sequenceName = "user_id_seq", initialValue = 4, allocationSize = 1, name = "userId_seq")
    private Integer userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Boolean isLoggedIn;

    private Boolean status;
}
