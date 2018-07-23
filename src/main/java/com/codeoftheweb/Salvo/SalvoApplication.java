package com.codeoftheweb.Salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.util.*;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

		@Bean
		public CommandLineRunner createGames (GameRepository gameRepository,
											  PlayerRepository playerRepository,
											  GamePlayerRepository gameplayerRepository,
											  ShipRepository shipRepository){
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


			List<String> locations1 = Arrays.asList("H2", "H3", "H4");
			List<String> locations2 = Arrays.asList("E1", "F1", "G1");
			List<String> locations3 = Arrays.asList("B4", "B5");
			List<String> locations4 = Arrays.asList("B5", "C5", "D5");
			List<String> locations5 = Arrays.asList("F1", "F2");
			List<String> locations6 = Arrays.asList("B5", "C5", "D5");
			List<String> locations7 = Arrays.asList("C6", "C7");
			List<String> locations8 = Arrays.asList("A2", "A3", "A4");
			List<String> locations9 = Arrays.asList("G6", "H6");
			List<String> locations10 = Arrays.asList("B5", "C5", "D5");
			List<String> locations11 = Arrays.asList("C6", "C7");
			List<String> locations12 = Arrays.asList("A2", "A3", "A4");
			List<String> locations13 = Arrays.asList("G6", "H6");
			List<String> locations14 = Arrays.asList("B5", "C5", "D5");
			List<String> locations15 = Arrays.asList("C6", "C7");
			List<String> locations16 = Arrays.asList("A2", "A3", "A4");
			List<String> locations17 = Arrays.asList("G6", "H6");
			List<String> locations18 = Arrays.asList("B5", "C5", "D5");
			List<String> locations19 = Arrays.asList("C6", "C7");
			List<String> locations20 = Arrays.asList("A2", "A3", "A4");
			List<String> locations21 = Arrays.asList("G6", "H6");
			List<String> locations22 = Arrays.asList("B5", "C5", "D5");
			List<String> locations23 = Arrays.asList("C6", "C7");
			List<String> locations24 = Arrays.asList("B5", "C5", "D5");
			List<String> locations25 = Arrays.asList("C6", "C7");
			List<String> locations26 = Arrays.asList("A2", "A3", "A4");
			List<String> locations27 = Arrays.asList("G6", "H6");


			Ship ship1 = new Ship("Destroyer", locations1, gamePlayer1);
			Ship ship2 = new Ship("Submarine", locations2, gamePlayer1);
			Ship ship3 = new Ship("Patrol Boat ", locations3, gamePlayer1);
			Ship ship4 = new Ship("Destroyer", locations4, gamePlayer2);
			Ship ship5 = new Ship("Patrol Boat ", locations5, gamePlayer2);
			Ship ship6 = new Ship("Destroyer", locations6, gamePlayer3);
			Ship ship7 = new Ship("Patrol Boat ", locations7, gamePlayer3);
			Ship ship8 = new Ship("Submarine", locations8, gamePlayer4);
			Ship ship9 = new Ship("Patrol Boat ", locations9, gamePlayer4);
			Ship ship10 = new Ship("Destroyer", locations10, gamePlayer5);
			Ship ship11 = new Ship("Patrol Boat ", locations11, gamePlayer5);
			Ship ship12 = new Ship("Submarine", locations12, gamePlayer6);
			Ship ship13 = new Ship("Patrol Boat ", locations13, gamePlayer6);
			Ship ship14 = new Ship("Destroyer", locations14, gamePlayer7);
			Ship ship15 = new Ship("Patrol Boat ", locations15, gamePlayer7);
			Ship ship16 = new Ship("Submarine", locations16, gamePlayer8);
			Ship ship17 = new Ship("Patrol Boat ", locations17, gamePlayer8);
			Ship ship18 = new Ship("Destroyer", locations18, gamePlayer9);
			Ship ship19 = new Ship("Patrol Boat ", locations19, gamePlayer9);
			Ship ship20 = new Ship("Submarine", locations20, gamePlayer10);
			Ship ship21 = new Ship("Patrol Boat ", locations21, gamePlayer10);
			Ship ship22 = new Ship("Destroyer", locations22, gamePlayer11);
			Ship ship23 = new Ship("Patrol Boat ", locations23, gamePlayer11);
			Ship ship24= new Ship("Destroyer", locations24, gamePlayer15);
			Ship ship25 = new Ship("Patrol Boat ", locations25, gamePlayer15);
			Ship ship26 = new Ship("Submarine", locations26, gamePlayer16);
			Ship ship27 = new Ship("Patrol Boat ", locations27, gamePlayer16);

			shipRepository.save(ship1);
			shipRepository.save(ship2);
			shipRepository.save(ship3);
			shipRepository.save(ship4);
			shipRepository.save(ship5);
			shipRepository.save(ship6);
			shipRepository.save(ship7);
			shipRepository.save(ship8);
			shipRepository.save(ship9);
			shipRepository.save(ship10);
			shipRepository.save(ship11);
			shipRepository.save(ship12);
			shipRepository.save(ship13);
			shipRepository.save(ship14);
			shipRepository.save(ship15);
			shipRepository.save(ship16);
			shipRepository.save(ship17);
			shipRepository.save(ship18);
			shipRepository.save(ship19);
			shipRepository.save(ship20);
			shipRepository.save(ship21);
			shipRepository.save(ship22);
			shipRepository.save(ship23);
			shipRepository.save(ship24);
			shipRepository.save(ship25);
			shipRepository.save(ship26);
			shipRepository.save(ship27);


			};
		}
	}



