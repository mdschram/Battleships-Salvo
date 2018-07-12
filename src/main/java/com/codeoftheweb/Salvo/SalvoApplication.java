package com.codeoftheweb.Salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

	}

		@Bean
		public CommandLineRunner innitData(PlayerRepository playerRepository) {
		return (args) -> {

			Player pol = new Player("pol@ubium.com", "pol");
			Player michiel = new Player("michiel@ubium.com", "michiel");

			playerRepository.save(pol);
			playerRepository.save(michiel);

		};
	}
}
