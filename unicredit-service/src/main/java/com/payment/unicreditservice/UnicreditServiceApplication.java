package com.payment.unicreditservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableFeignClients
@EnableEurekaClient
public class UnicreditServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnicreditServiceApplication.class, args);
	}

}
