package com.sep.Zuul;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "auth")
public interface AuthClient {

    @GetMapping("/verify")
    String verify(@RequestHeader("Auth-Token") String token);

    @GetMapping("/permission")
    String getPermission(@RequestHeader("Auth-Token") String token);
}
