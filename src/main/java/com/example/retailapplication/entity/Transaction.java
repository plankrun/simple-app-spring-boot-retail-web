package com.example.retailapplication.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Transaction entity to store transaction related data from and into database
 */
@Data
@Entity
public class Transaction {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="transactionId_seq")
    @SequenceGenerator(sequenceName = "transaction_id_seq", initialValue = 2, allocationSize = 1, name = "transactionId_seq")
    private Integer transactionId;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double tax;

    @Column(nullable = false)
    private Double total;
}
