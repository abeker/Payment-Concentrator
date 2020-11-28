package com.payment.paypalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableFeignClients
@EnableEurekaClient
public class PaypalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaypalServiceApplication.class, args);
	}

}
