package com.tingeso.ms_kart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsKartApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsKartApplication.class, args);
	}
}