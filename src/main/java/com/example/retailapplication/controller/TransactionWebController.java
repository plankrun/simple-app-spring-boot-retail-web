package com.example.retailapplication.controller;

import com.example.retailapplication.controller.rest.ProductController;
import com.example.retailapplication.controller.rest.TransactionController;
import com.example.retailapplication.dto.TransactionDTO;
import com.example.retailapplication.exception.RetailApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Controller that handles web endpoint for transaction
 */
@Controller
@Slf4j
@RequestMapping("/transaction")
public class TransactionWebController {

    private static final String TRANSACTION = "transaction";
    private static final String CHECKOUT = "checkout";
    private static final String PRODUCT = "product";

    @Autowired
    TransactionController transactionController;

    @Autowired
    ProductController productController;

    @GetMapping("")
    public ModelAndView showTransactionList() {
        ModelAndView mav = new ModelAndView(TRANSACTION);
        try {
            mav.addObject("transactionAll", transactionController.list());
        } catch (RetailApplicationException e) {
            mav.addObject("error", e.getMessage());
        }
        return mav;
    }

    @GetMapping("/checkout/{productId}")
    public ModelAndView showCheckout(@PathVariable("productId") Integer productId) {
        ModelAndView mav = new ModelAndView(CHECKOUT);
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .productId(productId)
                .build();
        mav.addObject(TRANSACTION, transactionDTO);
        mav.addObject(PRODUCT, productController.showProductDetail(productId));
        return mav;
    }

    @PostMapping("/checkout/{productId}")
    public ModelAndView doCheckout(@PathVariable("productId")Integer productId, @Valid TransactionDTO transactionDTO, BindingResult result) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav = addErrorObjectFromTransactionForm(result);
            transactionDTO = TransactionDTO.builder()
                    .productId(productId)
                    .build();
            mav.addObject(TRANSACTION, transactionDTO);
            mav.addObject(PRODUCT, productController.showProductDetail(productId));
            mav.setViewName(CHECKOUT);
            return mav;
        } else {
            try {
                transactionController.checkout(transactionDTO);
                mav.addObject(TRANSACTION, transactionController.list());
                mav.setViewName("redirect:/transaction");
            } catch (RetailApplicationException e) {
                mav.addObject("error", e.getMessage());
                transactionDTO.setProductId(productId);
                mav.addObject(TRANSACTION, transactionDTO);
                mav.addObject(PRODUCT, productController.showProductDetail(productId));
                mav.setViewName(CHECKOUT);
            }
        }
        return mav;
    }

    private ModelAndView addErrorObjectFromTransactionForm(BindingResult result) {
        ModelAndView mav = new ModelAndView();

        for (FieldError error : result.getFieldErrors()) {
            showFieldError(error);
            if ("userId".equals(error.getField())) {
                mav.addObject("errorUserId", error.getDefaultMessage());
            }
            if ("quantity".equals(error.getField())) {
                mav.addObject("errorQuantity", error.getDefaultMessage());
            }
        }
        return mav;
    }

    private void showFieldError(FieldError error) {
        log.info(error.getObjectName() + " - " + error.getDefaultMessage() + " - " + error.getField());
    }
}
