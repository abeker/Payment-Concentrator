package com.payment.pccservice.feign;

import com.payment.pccservice.dto.request.RequestPcc;
import com.payment.pccservice.dto.response.ResponsePcc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "unicredit")
public interface UnicreditClient {

    @PostMapping("/pay/pcc")
    ResponsePcc sendToClient(@RequestBody RequestPcc requestPcc);

}
