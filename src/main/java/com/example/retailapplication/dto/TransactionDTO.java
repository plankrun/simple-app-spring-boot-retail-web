package com.example.retailapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * DTO for storing Transaction related request to be parsed into Response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    @NotNull(message = "User ID is empty!")
    private Integer userId;

    @Positive(message = "Quantity must be larger than zero!")
    private Integer quantity;

    private Integer transactionId;
    private String username;
    private Integer productId;
    private String productName;
    private LocalDate transactionDate;
    private Double price;
    private Double subtotal;
    private Double tax;
    private Double total;
}
