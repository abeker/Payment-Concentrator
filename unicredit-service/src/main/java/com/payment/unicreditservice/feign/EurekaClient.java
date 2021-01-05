package com.payment.unicreditservice.feign;

import com.payment.unicreditservice.dto.response.RegisterBank;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "eureka-serviceregistry")
public interface EurekaClient {

    @GetMapping("/payment-type/bank")
    List<RegisterBank> getBanks();

}
