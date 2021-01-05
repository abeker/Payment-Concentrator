package com.payment.bankservice.feign;

import com.payment.bankservice.dto.response.RegisterBank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "eureka-serviceregistry")
public interface EurekaClient {

    @GetMapping("/payment-type/bank")
    List<RegisterBank> getBanks();

}
