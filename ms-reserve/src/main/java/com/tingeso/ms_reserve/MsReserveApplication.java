package com.tingeso.ms_reserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.tingeso.ms_reserve.clients")
public class MsReserveApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsReserveApplication.class, args);
	}
}