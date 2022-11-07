package com.shopme.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
public class AuthserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthserviceApplication.class, args);
	}

}
