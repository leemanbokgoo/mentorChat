package com.example.metoChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class MetoChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetoChatApplication.class, args);
	}

}
