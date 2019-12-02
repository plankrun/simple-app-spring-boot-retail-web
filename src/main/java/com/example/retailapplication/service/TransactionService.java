package com.example.retailapplication.service;

import com.example.retailapplication.dto.TransactionDTO;
import com.example.retailapplication.entity.Product;
import com.example.retailapplication.entity.Transaction;
import com.example.retailapplication.entity.User;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.exception.InvalidAmountException;
import com.example.retailapplication.exception.RetailApplicationException;
import com.example.retailapplication.exception.UserNotLoggedInException;
import com.example.retailapplication.util.mapper.TransactionMapper;
import com.example.retailapplication.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This class is used to process transaction from TransactionController
 */
@Service
@Slf4j
public class TransactionService  {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    private TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);

    /**
     * To get all transaction data
     * @return response that store data in the form of transaction DTO list
     * @throws DataNotFoundException when data is empty
     */
    public List<TransactionDTO> list() throws DataNotFoundException {
        List<Transaction> transactionList = transactionRepository.findAll();
        List<TransactionDTO> transactionDTOList = transactionMapper.transactionListToTransactionDtoList(transactionList);

        if (transactionDTOList.isEmpty()) {
            throw new DataNotFoundException("No transaction found!");
        }

        return transactionDTOList;
    }

    /**
     * To receive checkout request when user is buying products
     * @param userId to store userId from request
     * @param productId to store productId from request
     * @param quantity to store quantity from request
     * @return response that store data in the form of transaction DTO
     * @throws RetailApplicationException with the following details:
         * @throws InvalidAmountException when user balance is not enough, stock is not enough or empty, or invalid request quantity
         * @throws DataNotFoundException when user or product data is empty
         * @throws UserNotLoggedInException when user is not logged when trying to checkout
     */
    public TransactionDTO checkout(Integer userId, Integer productId, Integer quantity) throws RetailApplicationException {
        User user = validateUser(userId);
        Product product = validateProduct(productId);

        if (quantity < 1) {
            throw new InvalidAmountException("Quantity must be larger than 0!");
        }

        String username = user.getUsername();
        String productName = product.getProductName();
        Double price = product.getPrice();

        Double subtotal = price * quantity;
        Double tax = (subtotal * 0.1);
        Double total = subtotal + tax;

        if (user.getBalance() < total) {
            throw new InvalidAmountException("Insufficient user balance!");
        }

        if (product.getStock() - quantity < 0) {
            throw new InvalidAmountException("Stock is not enough!");
        }

        // Save transaction data
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .transactionDate(LocalDate.now())
                .userId(userId)
                .username(username)
                .productId(productId)
                .productName(productName)
                .price(price)
                .quantity(quantity)
                .subtotal(subtotal)
                .tax(tax)
                .total(total)
                .build();

        Transaction transaction = transactionMapper.transactionDtoToTransaction(transactionDTO);
        transactionRepository.save(transaction);
        transactionDTO.setTransactionId(transaction.getTransactionId());

        // Reduce user balance
        user.setBalance(user.getBalance() - transactionDTO.getTotal());
        userService.save(user);

        // Reduce product stock
        product.setStock(product.getStock() - transactionDTO.getQuantity());
        productService.save(product);

        return transactionDTO;
    }

    /**
     * To validate userId from request. User must be registered and logged in.
     * @param userId to store userId from request
     * @return validated User entity
     * @throws DataNotFoundException when user is not found in database
     * @throws UserNotLoggedInException when user is not logged in when trying to checkout
     */
    private User validateUser(Integer userId) throws DataNotFoundException, UserNotLoggedInException {
        Optional<User> optionalUser = userService.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new DataNotFoundException("User not found!");
        }
        User user = optionalUser.get();

        if (!user.getIsLoggedIn()) {
            throw new UserNotLoggedInException("User is not logged in or session is expired!");
        }

        return user;
    }

    /**
     * To validate productId and quantity from request. Product must be exist and quantity is not zero or negative
     * @param productId to store userId from request
     * @return validated Product entity
     * @throws DataNotFoundException when product is not found in database
     * @throws InvalidAmountException when quantity is zero or negative
     */
    private Product validateProduct(Integer productId) throws DataNotFoundException, InvalidAmountException {
        Optional<Product> optionalProduct = productService.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new DataNotFoundException("Product not found!");
        }
        Product product = optionalProduct.get();

        if (product.getStock() < 1) {
            throw new InvalidAmountException("Product stock is empty!");
        }

        return product;
    }
}
