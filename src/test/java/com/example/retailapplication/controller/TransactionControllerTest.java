package com.example.retailapplication.controller;

import com.example.retailapplication.controller.rest.TransactionController;
import com.example.retailapplication.dto.TransactionDTO;
import com.example.retailapplication.exception.DataNotFoundException;
import com.example.retailapplication.exception.InvalidAmountException;
import com.example.retailapplication.exception.RetailApplicationException;
import com.example.retailapplication.exception.UserNotLoggedInException;
import com.example.retailapplication.repository.TransactionRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionControllerTest {

    @Autowired
    TransactionController transactionController;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void getTransactionList() throws DataNotFoundException {
        // Assertion
        assertNotNull(transactionController.list());
    }

    @Test
    public void checkout() throws RetailApplicationException {
        // Actual
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .userId(3)
                .productId(1)
                .quantity(1)
                .build();

        TransactionDTO actual = transactionController.checkout(transactionDTO);

        // Expected
        TransactionDTO expected = TransactionDTO.builder()
                .transactionId(actual.getTransactionId())
                .transactionDate(actual.getTransactionDate())
                .userId(3)
                .username("Yanti")
                .productId(1)
                .productName("Kaos kaki Wonder Woman")
                .price(15000.00)
                .quantity(1)
                .subtotal(15000.00)
                .tax(1500.00)
                .total(16500.00)
                .build();

        // Assertion
        assertEquals(expected, actual);
    }

    /**
     * Start of exception test
     */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void transactionNotFoundError() throws DataNotFoundException {
        // Expected & Assertion
        expectedException.expect(DataNotFoundException.class);
        expectedException.expectMessage("No transaction found!");

        // Actual
        transactionRepository.deleteAll();
        transactionController.list();
    }

    @Test
    public void insufficientUserBalanceError() throws RetailApplicationException {
        // Expected & Assertion
        expectedException.expect(InvalidAmountException.class);
        expectedException.expectMessage("Insufficient user balance!");

        // Actual
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .userId(2)
                .productId(2)
                .quantity(10)
                .build();

        transactionController.checkout(transactionDTO);
    }

    @Test
    public void emptyProductStockError() throws RetailApplicationException {
        // Expected & Assertion
        expectedException.expect(InvalidAmountException.class);
        expectedException.expectMessage("Product stock is empty!");

        // Actual
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .userId(3)
                .productId(1)
                .quantity(5)
                .build();

        // Checkout to empty product stock
        transactionController.checkout(transactionDTO);

        // Checkout to trigger exception
        transactionController.checkout(transactionDTO);
    }

    @Test
    public void insufficientProductStockError() throws RetailApplicationException {
        // Expected & Assertion
        expectedException.expect(InvalidAmountException.class);
        expectedException.expectMessage("Stock is not enough!");

        // Actual
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .userId(3)
                .productId(1)
                .quantity(10)
                .build();

        transactionController.checkout(transactionDTO);
    }

    @Test
    public void userNotFoundError() throws RetailApplicationException {
        // Expected & Assertion
        expectedException.expect(DataNotFoundException.class);
        expectedException.expectMessage("User not found!");

        // Actual
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .userId(4)
                .productId(1)
                .quantity(1)
                .build();

        transactionController.checkout(transactionDTO);
    }

    @Test
    public void userNotLoggedInError() throws RetailApplicationException {
        // Expected & Assertion
        expectedException.expect(UserNotLoggedInException.class);
        expectedException.expectMessage("User is not logged in or session is expired!");

        // Actual
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .userId(1)
                .productId(1)
                .quantity(1)
                .build();

        transactionController.checkout(transactionDTO);
    }

    @Test
    public void productNotFoundError() throws RetailApplicationException {
        // Expected & Assertion
        expectedException.expect(DataNotFoundException.class);
        expectedException.expectMessage("Product not found!");

        // Actual
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .userId(3)
                .productId(4)
                .quantity(1)
                .build();

        transactionController.checkout(transactionDTO);
    }

    @Test
    public void invalidQuantityError() throws RetailApplicationException {
        // Expected & Assertion
        expectedException.expect(InvalidAmountException.class);
        expectedException.expectMessage("Quantity must be larger than 0!");

        // Actual
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .userId(3)
                .productId(1)
                .quantity(0)
                .build();

        transactionController.checkout(transactionDTO);
    }
}