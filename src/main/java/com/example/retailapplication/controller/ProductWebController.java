package com.example.retailapplication.controller;

import com.example.retailapplication.controller.rest.ProductController;
import com.example.retailapplication.dto.ProductDTO;
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
 * Controller that handles web endpoint for product
 */
@Slf4j
@Controller
@RequestMapping("/product")
public class ProductWebController {

    private static final String ADD_PRODUCT = "addproduct";
    private static final String UPDATE_PRODUCT = "updateproduct";
    private static final String PRODUCT = "product";
    private static final String ERROR = "error";
    private static final String REDIRECT_PRODUCT = "redirect:/product";

    @Autowired
    ProductController productController;

    @GetMapping("")
    public ModelAndView showProductList() {
        ModelAndView mav = new ModelAndView(PRODUCT);
        try {
            mav.addObject(PRODUCT, productController.list());
        } catch (RetailApplicationException e) {
            mav.addObject(ERROR, e.getMessage());
        }
        return mav;
    }

    @GetMapping("/{productId}")
    public ModelAndView showProductDetail(@PathVariable("productId") Integer productId) {
        ModelAndView mav = new ModelAndView("productdetail");
        mav.addObject(PRODUCT, productController.showProductDetail(productId));
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView showAddProduct() {
        ModelAndView mav = new ModelAndView(ADD_PRODUCT);
        mav.addObject(PRODUCT, new ProductDTO());
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView doAddProduct(@Valid ProductDTO productDTO, BindingResult result) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav = addErrorObjectFromProductForm(result);
            mav.addObject(PRODUCT, new ProductDTO());
            mav.setViewName(ADD_PRODUCT);
            return mav;
        } else {
            try {
                productController.add(productDTO);
                mav.addObject(PRODUCT, productController.list());
                mav.setViewName(REDIRECT_PRODUCT);
            } catch (RetailApplicationException e) {
                mav.addObject(ERROR, e.getMessage());
                mav.addObject(PRODUCT, new ProductDTO());
                mav.setViewName(ADD_PRODUCT);
            }
            return mav;
        }
    }

    @GetMapping("/update/{productId}")
    public ModelAndView showUpdateProduct(@PathVariable("productId") Integer productId) {
        ModelAndView mav = new ModelAndView(UPDATE_PRODUCT);
        mav.addObject(PRODUCT, productController.showProductDetail(productId));
        return mav;
    }

    @PostMapping("/update/{productId}")
    public ModelAndView doUpdateProduct(@PathVariable("productId") Integer productId, @Valid ProductDTO productDTO, BindingResult result) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav = addErrorObjectFromProductForm(result);
            mav.addObject(PRODUCT, productController.showProductDetail(productId));
            mav.setViewName(UPDATE_PRODUCT);
            return mav;
        } else {
            try {
                productController.update(productDTO);
                mav.addObject(PRODUCT, productController.list());
                mav.setViewName(REDIRECT_PRODUCT);
            } catch (RetailApplicationException e) {
                mav.addObject(ERROR, e.getMessage());
                mav.addObject(PRODUCT, productController.showProductDetail(productId));
                mav.setViewName(UPDATE_PRODUCT);
            }
            return mav;
        }
    }

    @GetMapping("/delete/{productId}")
    public ModelAndView deleteProduct(@PathVariable("productId") Integer productId) {
        ModelAndView mav = new ModelAndView("redirect:/delete/"+productId);
        try {
            mav.addObject(PRODUCT, productController.list());
            productController.delete(productId);
        } catch (RetailApplicationException e) {
            mav.addObject(ERROR, e.getMessage());
            mav.setViewName(PRODUCT);
        }
        return mav;
    }

    /**
     * Method for adding error object from add user or registration form to mav
     * @param result result of add user or registration form submission
     * @return mav with error object added
     */
    private ModelAndView addErrorObjectFromProductForm(BindingResult result) {
        ModelAndView mav = new ModelAndView();

        for (FieldError error : result.getFieldErrors()) {
            showFieldError(error);
            if ("productName".equals(error.getField())) {
                mav.addObject("errorProductName", error.getDefaultMessage());
            }
            if ("stock".equals(error.getField())) {
                mav.addObject("errorStock", error.getDefaultMessage());
            }
            if ("price".equals(error.getField())) {
                mav.addObject("errorPrice", error.getDefaultMessage());
            }
        }
        return mav;
    }

    private void showFieldError(FieldError error) {
        log.info(error.getObjectName() + " - " + error.getDefaultMessage() + " - " + error.getField());
    }
}
