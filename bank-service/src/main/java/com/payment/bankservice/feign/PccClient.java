package com.payment.bankservice.feign;

import com.payment.bankservice.dto.request.RequestPcc;
import com.payment.bankservice.dto.response.ResponsePcc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pcc")
public interface PccClient {

    @PostMapping("/")
    ResponsePcc sendToPcc(@RequestBody RequestPcc requestPcc);

}
