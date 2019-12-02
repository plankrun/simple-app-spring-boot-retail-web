package com.example.retailapplication.service;

import com.example.retailapplication.dto.ProductDTO;
import com.example.retailapplication.entity.Product;
import com.example.retailapplication.exception.ConflictException;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.exception.InvalidAmountException;
import com.example.retailapplication.exception.RetailApplicationException;
import com.example.retailapplication.util.mapper.ProductMapper;
import com.example.retailapplication.repository.ProductRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class is used to process product related request from ProductController
 */
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private static final String PRODUCT_NOT_FOUND = "Product not found!";

    /**
     * Show all product data in a list
     * @return a List of ProductDTO
     * @throws DataNotFoundException when there is no product on the database
     */
    public List<ProductDTO> list() throws DataNotFoundException {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = productMapper.productListToProductDtoList(productList);

        if (productDTOList.isEmpty()) {
            throw new DataNotFoundException(PRODUCT_NOT_FOUND);
        }

        return productDTOList;
    }

    /**
     * Add new product
     * @param productName name of the product
     * @param stock product stock
     * @param price product price
     * @return the newly added product in the form of productDTO
     * @throws RetailApplicationException with the following detail:
         * @throws DataNotFoundException when all the required data is not fulfilled
         * @throws ConflictException when the same product name already exist in the database
     **/
    public ProductDTO add(String productName, Integer stock, Double price) throws RetailApplicationException {
        if (productName.isEmpty() || stock == null || price == null) {
            throw new DataNotFoundException("Please fill all required data!");
        } else if (stock < 0) {
            throw new InvalidAmountException("Invalid stock amount!");
        } else if (price < 0 ) {
            throw new InvalidAmountException("Invalid price amount!");
        }

        if (productRepository.findByProductName(productName) != null) {
            throw new ConflictException("Product already exist!");
        }

        ProductDTO productDTO = ProductDTO.builder()
                .productName(productName)
                .stock(stock)
                .price(price)
                .build();

        Product product = productMapper.productDtoToProduct(productDTO);
        productRepository.save(product);
        productDTO.setProductId(product.getProductId());

        return productDTO;
    }

    /**
     * Delete product by productId
     * @param productId to be found
     * @throws DataNotFoundException when product with such id doesn't exist in the database
     */
    public void delete(Integer productId) throws DataNotFoundException {
        if (!productRepository.findById(productId).isPresent() || productId == null) {
            throw new DataNotFoundException(PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(productId);
    }

    /**
     * Update specified product with the data provided
     * @param productId to be updated
     * @param productName to replace the old productName
     * @param stock to replace the old stock
     * @param price to replace the old price
     * @return the updated product in the form of productDTO
     * @throws RetailApplicationException with the following detail:
         * @throws DataNotFoundException when all the required data is not fulfilled or when product with such id doesn't exist in the database
         * @throws InvalidAmountException when stock or price is less than zero
         * @throws ConflictException when the productName is already taken by a product with different productId
     **/
    public ProductDTO update(Integer productId, String productName, Integer stock, Double price) throws RetailApplicationException {
        if (productId == null || productName.isEmpty() || stock == null || price == null) {
            throw new DataNotFoundException("Please fill all required data!");
        } else if (stock < 0) {
            throw new InvalidAmountException("Invalid stock amount!");
        } else if (price < 0 ) {
            throw new InvalidAmountException("Invalid price amount!");
        }

        Optional<Product> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            throw new DataNotFoundException(PRODUCT_NOT_FOUND);
        }

        Product currentProduct = product.get();
        Product findProduct = productRepository.findByProductName(productName);

        if((findProduct != null) && !(findProduct.getProductId().equals(currentProduct.getProductId()))) {
            throw new ConflictException("Product with that name already exist!");
        }

        ProductDTO productDTO = ProductDTO.builder()
                .productId(productId)
                .productName(productName)
                .stock(stock)
                .price(price)
                .build();

        productRepository.save(productMapper.productDtoToProduct(productDTO));

        return productDTO;
    }

    void save(Product product) {
        productRepository.save(product);
    }

    Optional<Product> findById(Integer productId) {
        return productRepository.findById(productId);
    }

    /**
     * Find specific product
     * @param productId to be found
     * @return query result in the form of ProductDTO
     */
    public ProductDTO showProductDetail(Integer productId) {
        Product product = productRepository.findById(productId).get();
        return productMapper.productToProductDto(product);
    }
}
