package com.payment.unicreditservice.feign;

import com.payment.unicreditservice.dto.request.RequestPcc;
import com.payment.unicreditservice.dto.response.ResponsePcc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pcc")
public interface PccClient {

    @PostMapping("/")
    ResponsePcc sendToPcc(@RequestBody RequestPcc requestPcc);

}
