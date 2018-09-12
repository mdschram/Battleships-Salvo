package com.codeoftheweb.Salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
											  ShipRepository shipRepository,
											  SalvoRepository salvoRepository,
											  ScoreRepository scoreRepository
		){
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

				Player player1 = new Player("j.bauer@ctu.gov", "24");
				Player player2 = new Player("c.obrian@ctu.gov", "42");
				Player player3 = new Player("kim_bauer@gmail.com", "kb");
				Player player4 = new Player("t.almeida@ctu.gov", "mole");

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

				List<String> locations28 = Arrays.asList("B9", "C9", "D9", "E9", "F9");
				List<String> locations29 = Arrays.asList("G6", "G7", "G8", "G9");

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


			Ship ship1 = new Ship("fishingboat", locations1, gamePlayer1);
			Ship ship2 = new Ship("yacht", locations2, gamePlayer1);
			Ship ship3 = new Ship("speedboat", locations3, gamePlayer1);
			Ship ship4 = new Ship("fishingboat", locations4, gamePlayer2);
			Ship ship5 = new Ship("speedboat", locations5, gamePlayer2);
			Ship ship6 = new Ship("fishingboat", locations6, gamePlayer3);
			Ship ship7 = new Ship("speedboat", locations7, gamePlayer3);
			Ship ship8 = new Ship("yacht", locations8, gamePlayer4);
			Ship ship9 = new Ship("speedboat", locations9, gamePlayer4);
			Ship ship10 = new Ship("fishingboat", locations10, gamePlayer5);
			Ship ship11 = new Ship("speedboat", locations11, gamePlayer5);
			Ship ship12 = new Ship("yacht", locations12, gamePlayer6);
			Ship ship13 = new Ship("speedboat", locations13, gamePlayer6);
			Ship ship14 = new Ship("fishingboat", locations14, gamePlayer7);
			Ship ship15 = new Ship("speedboat", locations15, gamePlayer7);
			Ship ship16 = new Ship("yacht", locations16, gamePlayer8);
			Ship ship17 = new Ship("speedboat", locations17, gamePlayer8);
			Ship ship18 = new Ship("fishingboat", locations18, gamePlayer9);
			Ship ship19 = new Ship("speedboat", locations19, gamePlayer9);
			Ship ship20 = new Ship("yacht", locations20, gamePlayer10);
			Ship ship21 = new Ship("speedboat", locations21, gamePlayer10);
			Ship ship22 = new Ship("fishingboat", locations22, gamePlayer11);
			Ship ship23 = new Ship("speedboat", locations23, gamePlayer11);
			Ship ship24= new Ship("fishingboat", locations24, gamePlayer15);
			Ship ship25 = new Ship("speedboat", locations25, gamePlayer15);
			Ship ship26 = new Ship("yacht", locations26, gamePlayer16);
			Ship ship27 = new Ship("speedboat", locations27, gamePlayer16);
			Ship ship28 = new Ship("containership", locations28, gamePlayer1);
			Ship ship29 = new Ship("cruiseship", locations29, gamePlayer1);

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
			shipRepository.save(ship28);
			shipRepository.save(ship29);

				List<String> salvoLocations1 = Arrays.asList("B5", "C5", "");
				List<String> salvoLocations2 = Arrays.asList("B4", "B5", "B6");
				List<String> salvoLocations3 = Arrays.asList("", "D5", "");
				List<String> salvoLocations4 = Arrays.asList("E1", "H3", "A2");
				List<String> salvoLocations5 = Arrays.asList("A2", "A4", "G6");
				List<String> salvoLocations6 = Arrays.asList("B5", "D5", "C7");
				List<String> salvoLocations7 = Arrays.asList("A3", "H6", "");
				List<String> salvoLocations8 = Arrays.asList("C5", "C6", "");
				List<String> salvoLocations9 = Arrays.asList("G6", "H6", "A4");
				List<String> salvoLocations10 = Arrays.asList("H1", "H2", "H3");
				List<String> salvoLocations11 = Arrays.asList("A2", "A3", "D8");
				List<String> salvoLocations12 = Arrays.asList("E1", "F2", "G3");
				List<String> salvoLocations13 = Arrays.asList("A3", "A4", "F7");
				List<String> salvoLocations14 = Arrays.asList("B5", "C6", "H1");
				List<String> salvoLocations15 = Arrays.asList("A2", "G6", "H6");
				List<String> salvoLocations16 = Arrays.asList("C5", "C7", "D5");
				List<String> salvoLocations17 = Arrays.asList("A1", "A2", "A3");
				List<String> salvoLocations18 = Arrays.asList("B5", "B6", "C7");
				List<String> salvoLocations19 = Arrays.asList("G6", "G7", "G8");
				List<String> salvoLocations20 = Arrays.asList("C6", "D6", "E6");
				List<String> salvoLocations22 = Arrays.asList("H1", "H8", "");

				Salvo salvo1 = new Salvo(1, salvoLocations1, gamePlayer1);
				Salvo salvo2 = new Salvo(1, salvoLocations2, gamePlayer2);
				Salvo salvo3 = new Salvo(2, salvoLocations3, gamePlayer1);
				Salvo salvo4 = new Salvo(2, salvoLocations4, gamePlayer2);
				Salvo salvo5 = new Salvo(1, salvoLocations5, gamePlayer3);
				Salvo salvo6 = new Salvo(1, salvoLocations6, gamePlayer4);
				Salvo salvo7 = new Salvo(2, salvoLocations7, gamePlayer3);
				Salvo salvo8 = new Salvo(2, salvoLocations8, gamePlayer4);
				Salvo salvo9 = new Salvo(1, salvoLocations9, gamePlayer5);
				Salvo salvo10 = new Salvo(1, salvoLocations10, gamePlayer6);
				Salvo salvo11 = new Salvo(2, salvoLocations11, gamePlayer5);
				Salvo salvo12 = new Salvo(2, salvoLocations12, gamePlayer6);
				Salvo salvo13 = new Salvo(1, salvoLocations13, gamePlayer7);
				Salvo salvo14 = new Salvo(1, salvoLocations14, gamePlayer8);
				Salvo salvo15 = new Salvo(2, salvoLocations15, gamePlayer7);
				Salvo salvo16 = new Salvo(2, salvoLocations16, gamePlayer8);
				Salvo salvo17 = new Salvo(1, salvoLocations17, gamePlayer9);
				Salvo salvo18 = new Salvo(1, salvoLocations18, gamePlayer10);
				Salvo salvo19 = new Salvo(2, salvoLocations19, gamePlayer9);
				Salvo salvo20 = new Salvo(2, salvoLocations20, gamePlayer10);
				Salvo salvo22 = new Salvo(3, salvoLocations22, gamePlayer10);

				salvoRepository.save(salvo1);
				salvoRepository.save(salvo2);
				salvoRepository.save(salvo3);
				salvoRepository.save(salvo4);
				salvoRepository.save(salvo5);
				salvoRepository.save(salvo6);
				salvoRepository.save(salvo7);
				salvoRepository.save(salvo8);
				salvoRepository.save(salvo9);
				salvoRepository.save(salvo10);
				salvoRepository.save(salvo11);
				salvoRepository.save(salvo12);
				salvoRepository.save(salvo13);
				salvoRepository.save(salvo14);
				salvoRepository.save(salvo15);
				salvoRepository.save(salvo16);
				salvoRepository.save(salvo17);
				salvoRepository.save(salvo18);
				salvoRepository.save(salvo19);
				salvoRepository.save(salvo20);
				salvoRepository.save(salvo22);

				Score score1 = new Score(game1, player1, 1.0, date);
				Score score2 = new Score(game1, player2, 0.0, date);
				Score score3 = new Score(game2, player1, 0.5, date);
				Score score4 = new Score(game2, player2, 0.5, date);
				Score score5 = new Score(game3, player2, 1.0, date);
				Score score6 = new Score(game3, player4, 0.0, date);
				Score score7 = new Score(game4, player2, 0.5, date);
				Score score8 = new Score(game4, player1, 0.5, date);

				scoreRepository.save(score1);
				scoreRepository.save(score2);
				scoreRepository.save(score3);
				scoreRepository.save(score4);
				scoreRepository.save(score5);
				scoreRepository.save(score6);
				scoreRepository.save(score7);
				scoreRepository.save(score8);



			};
		}
	}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputName-> {
			Player player = playerRepository.findByUserName(inputName);
			if (player != null) {
				return new User(player.getUserName(), player.getPassWord(),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputName);
			}
		});
	}
}

@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/web/game.html").hasAuthority("USER")
				.and()
				.formLogin()
				.usernameParameter("name")
				.passwordParameter("pwd")
				.loginPage("/api/login");

		http.logout().logoutUrl("/api/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

		}

	}

}



