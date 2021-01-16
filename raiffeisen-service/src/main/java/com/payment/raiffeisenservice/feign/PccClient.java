package com.payment.raiffeisenservice.feign;

import com.payment.raiffeisenservice.dto.request.RequestPcc;
import com.payment.raiffeisenservice.dto.response.ResponsePcc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pcc")
public interface PccClient {

    @PostMapping("/")
    ResponsePcc sendToPcc(@RequestBody RequestPcc requestPcc);

}
