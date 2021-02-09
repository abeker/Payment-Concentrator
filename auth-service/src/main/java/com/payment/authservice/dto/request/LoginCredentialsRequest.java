package com.payment.authservice.dto.request;

import lombok.Data;

@Data
public class LoginCredentialsRequest {

    private String secret;
    private String password;

}
