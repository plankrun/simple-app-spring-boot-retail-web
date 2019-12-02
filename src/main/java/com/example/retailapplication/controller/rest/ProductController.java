package com.example.retailapplication.controller.rest;

import com.example.retailapplication.dto.ProductDTO;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.exception.RetailApplicationException;
import com.example.retailapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is used to receive product related requests
 */
@RestController
@RequestMapping("/rest/product")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * Show all product data in a list
     * @return a call to productService.list
     * @throws DataNotFoundException to handle error on productService
     */
    @GetMapping("/list")
    public List<ProductDTO> list() throws DataNotFoundException {
        return productService.list();
    }

    /**
     * Add new product
     * @param productDTO request in the form of ProductDTO (productName, stock, price)
     * @return a call to productService.add
     * @throws RetailApplicationException to handle error on productService
     */
    @PostMapping("/add")
    public ProductDTO add(@RequestBody ProductDTO productDTO) throws RetailApplicationException {
        return productService.add(productDTO.getProductName(), productDTO.getStock(), productDTO.getPrice());
    }

    /**
     * Delete product by productId
     * @param productId to be found
     * @throws DataNotFoundException when product with such id doesn't exist in the database
     */
    @PostMapping("/delete")
    public void delete(@RequestBody Integer productId) throws DataNotFoundException {
        productService.delete(productId);
    }


    /**
     * Update specified product with the data provided
     * @param productDTO new data to replace the old product data
     * @return a call to productService.update
     * @throws RetailApplicationException to handle error on productService
     */
    @PostMapping("/update")
    public ProductDTO update(@RequestBody ProductDTO productDTO) throws RetailApplicationException {
        return productService.update(productDTO.getProductId(), productDTO.getProductName(), productDTO.getStock(), productDTO.getPrice());
    }

    /**
     * Find specific product
     * @param productId to be found
     * @return a call to productService.showProductDetail
     */
    public ProductDTO showProductDetail(@RequestBody Integer productId) {
        return productService.showProductDetail(productId);
    }
}
