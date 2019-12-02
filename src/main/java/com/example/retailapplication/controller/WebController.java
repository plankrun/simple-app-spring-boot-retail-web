package com.example.retailapplication.controller;

import com.example.retailapplication.controller.rest.UserController;
import com.example.retailapplication.dto.UserDTO;
import com.example.retailapplication.exception.RetailApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller that handles web general endpoint consists of home, login, and logout
 */
@Controller
public class WebController {

    private static final String USER_LOGIN = "userLogin";

    @Autowired
    UserController userController;

    @GetMapping("/home")
    public ModelAndView showHome() {
         return new ModelAndView("home");
    }

    @GetMapping("/login")
    public ModelAndView showLogin() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject(USER_LOGIN, new UserDTO());
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView doLogin(UserDTO userDTO) {
        ModelAndView mav = new ModelAndView();
        try {
            mav.addObject(USER_LOGIN, userController.login(userDTO));
            mav.setViewName("redirect:/home");
        } catch(RetailApplicationException e) {

            mav.addObject(USER_LOGIN, new UserDTO());
            mav.addObject("error", e.getMessage());
            mav.setViewName("login");
        }
        return mav;
    }

    @GetMapping(value = {"/logout", "/"})
    public ModelAndView doLogout() {
        return new ModelAndView("redirect:/login");
    }
}
