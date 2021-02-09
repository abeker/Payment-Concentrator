package com.payment.authservice.services.implementation;

import com.payment.authservice.entity.User;
import com.payment.authservice.entity.UserDetailsFactory;
import com.payment.authservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository _userRepository;

    @Autowired
    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this._userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String secret) {
        final User user = _userRepository.findBySecret(secret);

        if (user == null) {
            throw new UsernameNotFoundException("User '" + secret + "' not found");
        }
        return UserDetailsFactory.create(user);
    }

}
