package com.example.quickprep;

import com.example.quickprep.config.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuickprepApplication {

	public static void main(String[] args) {
		Constants.API_KEY = args[0];
		SpringApplication.run(QuickprepApplication.class, args);
	}

}
