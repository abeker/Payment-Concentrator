package com.payment.pccservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
@EnableFeignClients
public class PccServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PccServiceApplication.class, args);
	}

}
