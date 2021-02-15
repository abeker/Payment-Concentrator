package com.payment.authservice.services.definition;


import com.payment.authservice.entity.User;

public interface IUserService {

    User getUserFromToken(String token);

    String getCurrentUser(String token);
}
