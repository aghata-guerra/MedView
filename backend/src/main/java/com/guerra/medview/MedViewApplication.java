package com.guerra.medview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MedViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedViewApplication.class, args);
	}

}
