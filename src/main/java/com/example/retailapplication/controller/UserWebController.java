package com.example.retailapplication.controller;

import com.example.retailapplication.controller.rest.UserController;
import com.example.retailapplication.dto.UserDTO;
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
 * Controller that handles web endpoint for user
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserWebController {

    private static final String UPDATE_USER = "updateuser";
    private static final String REDIRECT_USER = "redirect:/user";
    private static final String REGISTER = "register";
    private static final String ADD_USER = "adduser";
    private static final String ERROR = "error";

    @Autowired
    UserController userController;

    @GetMapping("")
    public ModelAndView showUserList() {
        ModelAndView mav = new ModelAndView("user");
        try {
            mav.addObject("user", userController.list());
        } catch (RetailApplicationException e) {
            mav.addObject(ERROR, e.getMessage());
        }
        return mav;
    }

    @GetMapping("/{userId}")
    public ModelAndView showUserDetail(@PathVariable("userId") Integer userId) {
        ModelAndView mav = new ModelAndView("userdetail");
        mav.addObject("user", userController.showUserDetail(userId));
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView showAddUser() {
       ModelAndView mav = new ModelAndView(ADD_USER);
       UserDTO userDTO = UserDTO.builder()
               .isLoggedIn(false)
               .balance(0.00)
               .build();
       mav.addObject("user", userDTO);
       return mav;
    }

    @PostMapping("/add")
    public ModelAndView doAddUser(@Valid UserDTO userDTO, BindingResult result) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav = addErrorObjectFromAddUserForm(result);
            mav.addObject("user", new UserDTO());
            mav.setViewName(ADD_USER);
            return mav;
        } else {
            try {
                userController.register(userDTO);
                mav.addObject("user", userController.list());
                mav.setViewName(REDIRECT_USER);
            } catch (RetailApplicationException e) {
                mav.addObject(ERROR, e.getMessage());
                mav.addObject("user", new UserDTO());
                mav.setViewName(ADD_USER);
            }
            return mav;
        }
    }

    @GetMapping("/update/{userId}")
    public ModelAndView showUpdateUser(@PathVariable("userId") Integer userId) {
        ModelAndView mav = new ModelAndView(UPDATE_USER);
        mav.addObject("user", userController.showUserDetail(userId));
        return mav;
    }

    @PostMapping("/update/{userId}")
    public ModelAndView doUpdateUser(@PathVariable("userId") Integer userId, @Valid UserDTO userDTO, BindingResult result) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav = addErrorObjectFromUpdateUserForm(result);
            mav.addObject("user", userController.showUserDetail(userId));
            mav.setViewName(UPDATE_USER);
            return mav;
        } else {
            try {
                userController.update(userDTO);
                mav.addObject("user", userController.list());
                mav.setViewName(REDIRECT_USER);
            } catch (RetailApplicationException e) {
                mav.addObject(ERROR, e.getMessage());
                mav.addObject("user", userController.showUserDetail(userId));
                mav.setViewName(UPDATE_USER);
            }
            return mav;
        }
    }

    @GetMapping("/delete/{userId}")
    public ModelAndView deleteUser(@PathVariable("userId") Integer userId) {
        ModelAndView mav = new ModelAndView(REDIRECT_USER);
        try {
            mav.addObject("user", userController.list());
            userController.delete(userId);
        } catch (RetailApplicationException e) {
            mav.addObject(ERROR, e.getMessage());
            mav.setViewName("user");
        }
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView showRegister() {
        ModelAndView mav = new ModelAndView(REGISTER);
        mav.addObject("user", new UserDTO());
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView doRegister(@Valid UserDTO userDTO, BindingResult result) {
        ModelAndView mav = new ModelAndView();

        if (result.hasErrors()) {
            mav = addErrorObjectFromAddUserForm(result);
            mav.addObject("user", new UserDTO());
            mav.setViewName(REGISTER);
            return mav;
        } else {
            try {
                userController.register(userDTO);
                mav.addObject("user", userController.list());
                mav.addObject("userLogin", new UserDTO());
                mav.setViewName("login");
            } catch (RetailApplicationException e) {
                mav.addObject(ERROR, e.getMessage());
                mav.addObject("user", new UserDTO());
                mav.setViewName(REGISTER);
            }
            return mav;
        }
    }

    /**
     * Method for adding error object from add user or registration form to mav
     * @param result result of add user or registration form submission
     * @return mav with error object added
     */
    private ModelAndView addErrorObjectFromAddUserForm(BindingResult result) {
        ModelAndView mav = new ModelAndView();

        for (FieldError error : result.getFieldErrors()) {
            showFieldError(error);
            if ("username".equals(error.getField())) {
                mav.addObject("errorUsername", error.getDefaultMessage());
            }
            if ("password".equals(error.getField())) {
                mav.addObject("errorPassword", error.getDefaultMessage());
            }
        }
        return mav;
    }

    /**
     * Method for adding error object from update user form to mav
     * @param result result of update user form submission
     * @return mav with error object added
     */
    private ModelAndView addErrorObjectFromUpdateUserForm(BindingResult result) {
        ModelAndView mav = new ModelAndView();

        for (FieldError error : result.getFieldErrors()) {
            showFieldError(error);
            if ("username".equals(error.getField())) {
                mav.addObject("errorUsername", error.getDefaultMessage());
            }
            if ("password".equals(error.getField())) {
                mav.addObject("errorPassword", error.getDefaultMessage());
            }
            if ("balance".equals(error.getField())) {
                mav.addObject("errorBalance", error.getDefaultMessage());
            }
        }
        return mav;
    }

    private void showFieldError(FieldError error) {
        log.info(error.getObjectName() + " - " + error.getDefaultMessage() + " - " + error.getField());
    }
}
