package com.example.retailapplication.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity that store roles of user for Spring Security purposes
 */
@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String roleName;
}
