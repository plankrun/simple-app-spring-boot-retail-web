package com.example.retailapplication.controller;

import com.example.retailapplication.controller.rest.ProductController;
import com.example.retailapplication.dto.ProductDTO;
import com.example.retailapplication.entity.Product;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.util.mapper.ProductMapper;
import com.example.retailapplication.repository.ProductRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerTest {

    @Autowired
    ProductController productController;

    @Autowired
    ProductRepository productRepository;

    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    public void getProductList() throws DataNotFoundException {
        // Assertion
        assertNotNull(productController.list());
    }

    /**
     * Start of exception test
     */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void productNotFoundError() throws DataNotFoundException {
        // Expected & Assertion
        expectedException.expect(DataNotFoundException.class);
        expectedException.expectMessage("Product not found!");

        // Actual
        productRepository.deleteAll();
        productController.list();
    }

    @Test
    public void productDTOTest() {
        // Expected
        ProductDTO productDTOExpected = ProductDTO.builder()
                .productId(1)
                .productName("Kaos kaki Wonder Woman")
                .stock(5)
                .price(15000.00)
                .build();

        // Actual
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDtoActual = productMapper.productListToProductDtoList(productList);

        // Assertion
        assertEquals(productDTOExpected.getProductId(), productDtoActual.get(0).getProductId());
        assertEquals(productDTOExpected.getProductName(), productDtoActual.get(0).getProductName());
        assertEquals(productDTOExpected.getStock(), productDtoActual.get(0).getStock());
        assertEquals(productDTOExpected.getPrice(), productDtoActual.get(0).getPrice());

    }
}