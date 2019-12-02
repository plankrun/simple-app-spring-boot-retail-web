package com.example.retailapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * DTO for storing Product related request to be parsed into Response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Integer productId;

    @NotEmpty(message = "Product name is empty!")
    @Size(min = 3, max = 30, message = "Product name must be from 3 to 30 characters!")
    private String productName;

    @PositiveOrZero(message = "Stock must be zero or larger")
    private Integer stock;

    @PositiveOrZero(message = "Price must be zero or larger")
    private Double price;
}
