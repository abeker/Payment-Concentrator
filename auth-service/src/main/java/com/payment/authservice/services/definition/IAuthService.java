package com.payment.authservice.services.definition;

import com.payment.authservice.dto.request.LoginCredentialsRequest;
import com.payment.authservice.dto.response.UserResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

public interface IAuthService {

    UserResponse login(LoginCredentialsRequest request, HttpServletRequest httpServletRequest) throws SQLException;

    String getPermission(String token);

    UserResponse getUser(UUID userId);

}
