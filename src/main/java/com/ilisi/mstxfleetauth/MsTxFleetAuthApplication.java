package com.ilisi.mstxfleetauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsTxFleetAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsTxFleetAuthApplication.class, args);
	}
}
