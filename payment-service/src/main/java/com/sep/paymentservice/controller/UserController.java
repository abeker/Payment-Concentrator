package com.sep.paymentservice.controller;

import com.sep.paymentservice.services.impl.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService _userService;

    public UserController(UserService userService) {
        _userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

}
