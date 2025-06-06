package com.tingeso.ms_6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.tingeso.ms_6.clients")
public class Ms6Application {

	public static void main(String[] args) {
		SpringApplication.run(Ms6Application.class, args);
	}

}
