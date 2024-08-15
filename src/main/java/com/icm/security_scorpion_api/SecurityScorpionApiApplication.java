package com.icm.security_scorpion_api;

import com.icm.security_scorpion_api.config.MyWebSocketHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityScorpionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityScorpionApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(MyWebSocketHandler webSocketHandler) {
		return args -> {
			// Envía el mensaje "reboot" a todos los clientes WebSocket cuando la aplicación se inicie
			webSocketHandler.sendRebootMessage();
		};
	}
}
