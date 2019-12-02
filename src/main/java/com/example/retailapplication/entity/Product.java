package com.example.retailapplication.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Product entity to store product related data from and into database
 */
@Data
@Entity
public class Product {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="productId_seq")
    @SequenceGenerator(sequenceName = "product_id_seq", initialValue = 4, allocationSize = 1, name = "productId_seq")
    private Integer productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Double price;
}
