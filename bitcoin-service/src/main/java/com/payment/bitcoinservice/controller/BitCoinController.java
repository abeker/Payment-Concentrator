package com.payment.bitcoinservice.controller;

import com.payment.bitcoinservice.controller.dto.OrderDto;
import com.payment.bitcoinservice.controller.dto.OrderResponseDto;
import com.payment.bitcoinservice.model.BitcoinOrder;
import com.payment.bitcoinservice.model.Customer;
import com.payment.bitcoinservice.model.OrderType;
import com.payment.bitcoinservice.service.BitcoinPayment;
import com.payment.bitcoinservice.service.CustomerService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.PathParam;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class BitCoinController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private BitcoinPayment bitcoinPayment;

    @Value("${coingate.authkey}")
    private String authKey;

    @CrossOrigin(origins = "*")
    @GetMapping("/pay")
    public String pay(){
        return "Successfully paid using bitcoin";
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>>getAllCustomers(){
        List<Customer> customers =  customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/order")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderDto orderDto) throws JSONException {
        UUID uuid = UUID.randomUUID();
        orderDto.setOrder_id(uuid.toString());
        orderDto.setSuccess_url("http://localhost:8083/order/success?order_id="+orderDto.getOrder_id());
        orderDto.setCallback_url("http://localhost:8083/order/callback");
        orderDto.setCancel_url("http://localhost:8083/order/cancel?oder_id" + orderDto.getOrder_id());
        String customToken = "custom-token";
        orderDto.setCallback_token(customToken);

        BitcoinOrder order = createOrderFromDto(orderDto);
        BitcoinOrder saved = bitcoinPayment.saveOrder(order);

        //Sending request
        String coingateUrl = "https://api-sandbox.coingate.com/v2/orders";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authKey);
        JSONObject obj = new JSONObject();
        obj.put("order_id", orderDto.getOrder_id());
        obj.put("price_amount", orderDto.getPrice_amount());
        obj.put("price_currency",orderDto.getPrice_currency());
        obj.put("receive_currency",orderDto.getReceive_currency());
        obj.put("callback_url",orderDto.getCallback_url());
        obj.put("success_url", orderDto.getSuccess_url());
        obj.put("cancel_url", orderDto.getCancel_url());
        obj.put("token", orderDto.getCallback_token());

        HttpEntity<String> request = new HttpEntity<>(obj.toString(), headers);
        OrderResponseDto orderResponseDto = restTemplate.postForObject(coingateUrl, request, OrderResponseDto.class);
        System.out.println(orderResponseDto.toString());
        return ResponseEntity.ok(orderResponseDto);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/order/success")
    public ResponseEntity<String> orderSuccess(@RequestParam("order_id") String orderID){
        System.out.println(orderID);
        bitcoinPayment.successfullPayment(orderID);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:3000/success"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/order/cancel")
    public ResponseEntity<String> orderCancel(@RequestParam("order_id") String orderID){
        bitcoinPayment.cancelledPayment(orderID);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:3000/canceled"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/callback")
    public ResponseEntity<String> orderCallback(){
        return ResponseEntity.ok("Order Callback");
    }

    public BitcoinOrder createOrderFromDto(OrderDto orderDto){
        BitcoinOrder order = new BitcoinOrder();

        order.setPriceCurrency(orderDto.getPrice_currency());
        order.setReceiveCurrency(orderDto.getReceive_currency());
        order.setStatus(OrderType.NEW);
        order.setOrderId(orderDto.getOrder_id());
        order.setPriceAmount(orderDto.getPrice_amount());
        return order;
    }
}
