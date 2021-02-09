package com.payment.authservice.services.implementation;

import com.payment.authservice.entity.User;
import com.payment.authservice.repository.IUserRepository;
import com.payment.authservice.security.TokenUtils;
import com.payment.authservice.services.definition.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserRepository _userRepository;
    private final TokenUtils _tokenUtils;

    public UserService(IUserRepository userRepository, TokenUtils tokenUtils) {
        _userRepository = userRepository;
        _tokenUtils = tokenUtils;
    }

    @Override
    public User getUserFromToken(String token) {
        String secret = _tokenUtils.getSecretFromToken(token);
        return _userRepository.findBySecret(secret);
    }

    @Override
    public String getCurrentUser(String token) {
        User currentUser = getUserFromToken(token);
        return currentUser.getName();
    }

}
