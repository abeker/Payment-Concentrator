package com.payment.authservice.controller;

import com.payment.authservice.dto.request.LoginCredentialsRequest;
import com.payment.authservice.dto.response.UserResponse;
import com.payment.authservice.security.TokenUtils;
import com.payment.authservice.services.definition.IAuthService;
import com.payment.authservice.services.definition.IUserService;
import com.payment.authservice.util.exceptions.GeneralException;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.UUID;

@SuppressWarnings({"unused", "RedundantThrows", "FieldCanBeLocal"})
@RestController
@RequestMapping("")
public class AuthController {

    private final IUserService _userService;
    private final IAuthService _authService;
    private final TokenUtils _tokenUtils;

    public AuthController(IUserService userService, IAuthService authService, TokenUtils tokenUtils) {
        _userService = userService;
        _authService = authService;
        _tokenUtils = tokenUtils;
    }

    @GetMapping("/verify")
    public String verify(@RequestHeader("Auth-Token") String token) throws NotFoundException {
        return _tokenUtils.getSecretFromToken(token);
    }

    @GetMapping("/permission")
    public String getPermissions(@RequestHeader("Auth-Token") String token) throws NotFoundException {
        return _authService.getPermission(token);
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return new ResponseEntity<>("Hello from auth service", HttpStatus.OK);
    }

    @PutMapping("/login")
    public UserResponse login(@RequestBody LoginCredentialsRequest request, HttpServletRequest httpServletRequest) throws GeneralException, SQLException {
        return _authService.login(request, httpServletRequest);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable("id") UUID userId) throws GeneralException {
        return _authService.getUser(userId);
    }

    @GetMapping("/{token}/current-user")
    public String getCurrentUser(@PathVariable("token") String token) throws GeneralException {
        return _userService.getCurrentUser(token);
    }


}
