package com.example.retailapplication.controller.rest;

import com.example.retailapplication.dto.TransactionDTO;
import com.example.retailapplication.exception.RetailApplicationException;
import com.example.retailapplication.exception.UserNotLoggedInException;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.exception.InvalidAmountException;
import com.example.retailapplication.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is used to receive transaction related requests
 */
@RestController
@RequestMapping("/rest/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    /**
     * To get all transaction data
     * @return response that store data in the form of transaction DTO list
     * @throws DataNotFoundException when data is empty
     */
    @GetMapping("/list")
    public List<TransactionDTO> list() throws DataNotFoundException {
        return transactionService.list();
    }

    /**
     * To receive checkout request when user is buying products
     * @param transactionDTO to store request
     * @return response that store data in the form of transaction DTO
     * @throws RetailApplicationException with the following details:
         * @throws InvalidAmountException when user balance is not enough, stock is not enough or empty, or invalid request quantity
         * @throws DataNotFoundException when user or product data is empty
         * @throws UserNotLoggedInException when user is not logged when trying to checkout
     */
    @PostMapping("/checkout")
    public TransactionDTO checkout(@RequestBody TransactionDTO transactionDTO) throws RetailApplicationException {
        return transactionService.checkout(transactionDTO.getUserId(), transactionDTO.getProductId(), transactionDTO.getQuantity());
    }
}
