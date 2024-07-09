package com.example.binanceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BinanceappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BinanceappApplication.class, args);
	}

}
