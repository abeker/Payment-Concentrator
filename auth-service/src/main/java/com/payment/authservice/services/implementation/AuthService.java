package com.payment.authservice.services.implementation;

import com.payment.authservice.dto.request.LoginCredentialsRequest;
import com.payment.authservice.dto.response.UserResponse;
import com.payment.authservice.entity.User;
import com.payment.authservice.entity.UserDetailsImpl;
import com.payment.authservice.repository.IUserRepository;
import com.payment.authservice.security.TokenUtils;
import com.payment.authservice.services.definition.IAuthService;
import com.payment.authservice.util.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class AuthService implements IAuthService {

    private final AuthenticationManager _authenticationManager;
    private final TokenUtils _tokenUtils;
    private final IUserRepository _userRepository;
    private final PasswordEncoder _passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, TokenUtils tokenUtils, IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        _authenticationManager = authenticationManager;
        _tokenUtils = tokenUtils;
        _userRepository = userRepository;
        _passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse login(LoginCredentialsRequest request, HttpServletRequest httpServletRequest) {
        User user = _userRepository.findBySecret(request.getSecret());
        if(!isUserFound(user, request)) {
            throw new GeneralException("Bad credentials.", HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = loginUser(request.getSecret(), request.getPassword());
        return createLoginUserResponse(authentication, user);
    }

    private Authentication loginUser(String secret, String password) {
        Authentication authentication = null;
        try {
            authentication = _authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(secret, password));
        }catch (BadCredentialsException e){
            throw new GeneralException("Bad credentials.", HttpStatus.BAD_REQUEST);
        }catch (DisabledException e){
            throw new GeneralException("Your registration request hasn't been approved yet.", HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private UserResponse createLoginUserResponse(Authentication authentication, User user) {
        UserDetailsImpl userLog = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = _tokenUtils.generateToken(userLog.getUsername());
        int expiresIn = _tokenUtils.getExpiredIn();

        UserResponse userResponse = mapUserToUserResponse(user);
        userResponse.setToken(jwt);
        userResponse.setTokenExpiresIn(expiresIn);

        return userResponse;
    }

    private boolean isUserFound(User user, LoginCredentialsRequest request) {
        return user != null && _passwordEncoder.matches(request.getPassword(), user.getPassword());
    }

    @Override
    public String getPermission(String token) {
        String secret = _tokenUtils.getSecretFromToken(token);
        System.out.println(secret);
        User user = _userRepository.findBySecret(secret);
        StringBuilder retVal = new StringBuilder();
        for (GrantedAuthority authority : user.getAuthorities()) {
            retVal.append(authority.getAuthority()).append(",");
        }
        return retVal.substring(0,retVal.length()-1);
    }

    @Override
    public UserResponse getUser(UUID userId) {
        User user = _userRepository.findOneById(userId);
        throwExceptionIfUserNull(user);
        return mapUserToUserResponse(user);
    }

    private void throwExceptionIfUserNull(User user) throws GeneralException {
        if(user == null) {
            throw new GeneralException("This user doesn't exist.", HttpStatus.BAD_REQUEST);
        }
    }

    private UserResponse mapUserToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());

        userResponse.setUsername(user.getName());
        userResponse.setUserRole(user.getUserRole().toString());
        return userResponse;
    }
}
