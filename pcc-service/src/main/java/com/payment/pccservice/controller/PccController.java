package com.payment.pccservice.controller;

import com.payment.pccservice.dto.request.RequestPcc;
import com.payment.pccservice.dto.response.ResponsePcc;
import com.payment.pccservice.service.definition.IPccService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/")
public class PccController {

    private final IPccService _pccService;

    public PccController(IPccService pccService) {
        _pccService = pccService;
    }

    @PostMapping()
    public ResponseEntity<?> checkPaymentRequest(@RequestBody RequestPcc requestPcc) {
        return new ResponseEntity<ResponsePcc>(_pccService.forwardRequest(requestPcc), HttpStatus.OK);
    }

}
