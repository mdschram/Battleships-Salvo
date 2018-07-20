package com.codeoftheweb.Salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

		@Bean
		public CommandLineRunner createGames (GameRepository gameRepository,
											  PlayerRepository playerRepository,
											  GamePlayerRepository gameplayerRepository){
			return (args) -> {

				Date date = new Date();
				Game game1 = new Game(date);
				Game game2 = new Game(date);
				Game game3 = new Game(date);
				Game game4 = new Game(date);
				Game game5 = new Game(date);
				Game game6 = new Game(date);
				Game game7 = new Game(date);
				Game game8 = new Game(date);

				game2.setDate(game2.calculateDate(3600));
				game3.setDate(game3.calculateDate(7200));

				gameRepository.save(game1);
				gameRepository.save(game2);
				gameRepository.save(game3);
				gameRepository.save(game4);
				gameRepository.save(game5);
				gameRepository.save(game6);
				gameRepository.save(game7);
				gameRepository.save(game8);

				Player player1 = new Player("j.bauer@ctu.gov");
				Player player2 = new Player("c.obrian@ctu.gov");
				Player player3 = new Player("kim_bauer@gmail.com");
				Player player4 = new Player("t.almeida@ctu.gov");

				playerRepository.save(player1);
				playerRepository.save(player2);
				playerRepository.save(player3);
				playerRepository.save(player4);

			GamePlayer gamePlayer1 = new GamePlayer(player1, game1, date);
			GamePlayer gamePlayer2 = new GamePlayer(player2, game1, date);
			GamePlayer gamePlayer3 = new GamePlayer(player1, game2, date);
			GamePlayer gamePlayer4 = new GamePlayer(player2, game2, date);
			GamePlayer gamePlayer5 = new GamePlayer(player2, game3, date);
			GamePlayer gamePlayer6 = new GamePlayer(player4, game3, date);
			GamePlayer gamePlayer7 = new GamePlayer(player2, game4, date);
			GamePlayer gamePlayer8 = new GamePlayer(player1, game4, date);
			GamePlayer gamePlayer9 = new GamePlayer(player4, game5, date);
			GamePlayer gamePlayer10 = new GamePlayer(player1, game5, date);
			GamePlayer gamePlayer11 = new GamePlayer(player3, game6, date);
			GamePlayer gamePlayer13 = new GamePlayer(player4, game7, date);
			GamePlayer gamePlayer15= new GamePlayer(player3, game8, date);
			GamePlayer gamePlayer16 = new GamePlayer(player4, game8, date);

			gameplayerRepository.save(gamePlayer1);
			gameplayerRepository.save(gamePlayer2);
			gameplayerRepository.save(gamePlayer3);
			gameplayerRepository.save(gamePlayer4);
			gameplayerRepository.save(gamePlayer5);
			gameplayerRepository.save(gamePlayer6);
			gameplayerRepository.save(gamePlayer7);
			gameplayerRepository.save(gamePlayer8);
			gameplayerRepository.save(gamePlayer9);
			gameplayerRepository.save(gamePlayer10);
			gameplayerRepository.save(gamePlayer11);
			gameplayerRepository.save(gamePlayer13);
			gameplayerRepository.save(gamePlayer15);
			gameplayerRepository.save(gamePlayer16);
			};
		}
	}



