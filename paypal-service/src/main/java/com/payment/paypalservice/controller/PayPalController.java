package com.payment.paypalservice.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.payment.paypalservice.dto.Order;
import com.payment.paypalservice.model.*;
import com.payment.paypalservice.service.PaymentService;
import com.payment.paypalservice.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.codec.binary.Base64;
import com.paypal.base.rest.PayPalRESTException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;

@RestController
public class PayPalController {

    @Autowired
    PaypalService payPalService;

    @Autowired
    PaymentService paymentService;

    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/pay")
    public String payWithPayPal(){
        return "Successfully paid with PayPal!";
    }

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

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws JSONException {
        String token = accessToken();

        String paypalUrl = "https://api-m.sandbox.paypal.com/v1/catalogs/products";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        JSONObject obj = new JSONObject();
        obj.put("name", product.getName());
        obj.put("description", product.getDescription());
        obj.put("type", product.getType());
        obj.put("category", product.getCategory());
        obj.put("image_url",  product.getImage_url());

        HttpEntity<String> request = new HttpEntity<>(obj.toString(), headers);
        Product productResponse = restTemplate.postForObject(paypalUrl, request, Product.class);
        System.out.println(productResponse);

        return ResponseEntity.ok(productResponse);
    }

    @PostMapping("/plans")
    public ResponseEntity<SubscriptionPlan> createSubscriptionPlan(@RequestBody SubscriptionPlanDto subscriptionPlanDto) throws JSONException {
        String token = accessToken();

        String paypalUrl = "https://api-m.sandbox.paypal.com/v1/billing/plans";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        JSONObject obj = new JSONObject();
//        obj.put("product_id", productId);
//        obj.put("name", "Book renting service plan");
//        obj.put("description", "Book renting service plan, per month charging.");
//        obj.put("status", "ACTIVE");
        SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
        subscriptionPlan.setProduct_id(subscriptionPlanDto.getProduct_id());
        subscriptionPlan.setName("Book renting service plan");
        subscriptionPlan.setDescription("Book renting service plan, per month charging");
        subscriptionPlan.setStatus("ACTIVE");
        // Create Biiling cycle
        Frequency f = new Frequency("MONTH",1);
        Gson g = new Gson();
        Gson builder = new GsonBuilder().create();

        //First Billing Cycle
        BillingCycle billingCycle = new BillingCycle();
        PricingScheme pricingScheme = new PricingScheme(new FixedPrice("10","USD"));
        billingCycle.setFrequency(f);
        billingCycle.setTenure_type("TRIAL");
        billingCycle.setSequence(1);
        billingCycle.setTotal_cycles(1);
        billingCycle.setPricing_scheme(pricingScheme);
        //Second Billing cycle
        BillingCycle billingCycle2 = new BillingCycle();
        billingCycle2.setFrequency(f);
        billingCycle2.setTenure_type("REGULAR");
        billingCycle2.setSequence(2);
        billingCycle2.setTotal_cycles(12);

        billingCycle2.setPricing_scheme(pricingScheme);
        ArrayList<BillingCycle> billingCycles = new ArrayList<>();
        billingCycles.add(billingCycle);
        billingCycles.add(billingCycle2);

        subscriptionPlan.setBilling_cycles(billingCycles);

        SetupFee setupFee = new SetupFee("10","USD");
        PaymentPreferences paymentPreferences = new PaymentPreferences();
        paymentPreferences.setAuto_bill_outstanding(true);
        paymentPreferences.setSetup_fee(setupFee);
        paymentPreferences.setSetup_fee_failure_action("CONTINUE");
        paymentPreferences.setPayment_failure_threshold(3);
//        String paymentReferencesJSON = g.toJson(paymentPreferences);
//        obj.put("payment_references", paymentReferencesJSON);
        subscriptionPlan.setPayment_preferences(paymentPreferences);
        Taxes taxes = new Taxes("10",false);
        subscriptionPlan.setTaxes(taxes);

        System.out.println(builder.toJson(subscriptionPlan));
        HttpEntity<String> request = new HttpEntity<>(builder.toJson(subscriptionPlan), headers);
        SubscriptionPlan subscriptionPlan1= restTemplate.postForObject(paypalUrl, request, SubscriptionPlan.class);
        System.out.println(subscriptionPlan1);
        return ResponseEntity.ok(subscriptionPlan1);
    }

    public String accessToken(){
        String tokenUrl = "https://api.sandbox.paypal.com/v1/oauth2/token";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "client_credentials");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        AuthToken token = restTemplate.postForObject(tokenUrl, request, AuthToken.class);
        if(token != null){
            return token.getAccess_token();
        }

        return "";
    }

    HttpHeaders createHeaders(){
        return new HttpHeaders() {{
            String auth = clientId + ":" + clientSecret;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
