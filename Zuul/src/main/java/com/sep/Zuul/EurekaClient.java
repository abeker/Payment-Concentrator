package com.sep.Zuul;

import com.sep.Zuul.dto.PaymentTypes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "eureka-serviceregistry")
public interface EurekaClient {

    @GetMapping("/literary-association/{lu_id}/{payment_type}")
    boolean hasPaymentType(@PathVariable("lu_id") String lu_id, @PathVariable("payment_type") String payment_type);

    @PostMapping("/literary-association/{lu_id}")
    void registerLiteraryAssociation(@PathVariable("lu_id") String luId, @RequestBody PaymentTypes paymentTypes);

}
