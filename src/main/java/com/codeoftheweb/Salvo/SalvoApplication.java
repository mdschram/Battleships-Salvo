package com.codeoftheweb.Salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);

	}

		@Bean
		public CommandLineRunner initData(GameRepository gameRepository) {
		return (args) -> {

			LocalDate date = LocalDate.now();
			System.out.println(date);
			Game game1 = new Game(date);
			Game game2 = new Game(date);
			Game game3 = new Game(date);

			gameRepository.save(game1);
			gameRepository.save(game2);
			gameRepository.save(game3);

		};
	}
}


