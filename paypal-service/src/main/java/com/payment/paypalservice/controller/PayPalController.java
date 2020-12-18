package com.payment.paypalservice.controller;

import com.payment.paypalservice.dto.Order;
import com.payment.paypalservice.service.PaymentService;
import com.payment.paypalservice.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class PayPalController {

    @Autowired
    PaypalService payPalService;

    @Autowired
    PaymentService paymentService;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @CrossOrigin(value = "*")
    @GetMapping("/pay")
    public String payWithPayPal(){
        return "Successfully paid with PayPal!";
    }

    @CrossOrigin(value = "*", origins = "*")
    @PostMapping("/pay")
    public String payment(@RequestBody Order order) throws PayPalRESTException {
        Payment payment = payPalService.createPayment(order.getPrice(),order.getCurrency(),order.getMethod(),order.getIntent(),
                order.getDescription(),"http://localhost:8082/" + CANCEL_URL,"http://localhost:8082/"+SUCCESS_URL);
        for(Links links : payment.getLinks()){
            if(links.getRel().equals("approval_url")){
                return links.getHref();
            }
        }
        return "redirect:/";
    }

    @GetMapping(value = SUCCESS_URL)
    public ResponseEntity<String> successPay(@RequestParam("paymentId")String paymentId, @RequestParam("PayerID")String payerId) throws PayPalRESTException, URISyntaxException {
        Payment payment = payPalService.executePayment(paymentId,payerId);
        System.out.println(payment.toJSON());
        //OVDE SACUVATI U BAZI PODATKE O PAYMENTU
        paymentService.savePayment(payment);

        if(payment.getState().equals("approved")){
            URI yahoo = new URI("http://localhost:3000/success");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(yahoo);
            return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You dont have sufficient money");
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay(){
        return "canceled";
    }

}
