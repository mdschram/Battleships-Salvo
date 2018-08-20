package com.codeoftheweb.Salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GamePlayerRepository gameplayerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @RequestMapping("/games")
    public List<Object> getGames() {
        return gameRepository
                .findAll()
                .stream()
                .map(game -> makeGameDTO(game))
                .collect(toList());
    }

    @RequestMapping("/players")
    public List<Object> getPlayers() {
        return playerRepository
                .findAll()
                .stream()
                .map(b -> b.getUserName())
                .collect(toList());
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String, Object> getGamePlayerData(@PathVariable Long gamePlayerId) {
        GamePlayer user = gameplayerRepository.findOne(gamePlayerId);
        Map<String, Object> gameView = new LinkedHashMap<>();
        gameView.put("game", makeGameDTO(user.getGame()));
        gameView.put("ships", user.getShips()
                .stream()
                .map(thisShip -> makeShipDTO(thisShip))
                .collect(toList()));
        gameView.put("usersalvoes", getUserSalvos(user));
        gameView.put("enemysalvoes", getEnemySalvos(user));
        return gameView;
    }

    @RequestMapping("/scoreboard")
    public Set<Object> getScores() {
        return playerRepository
                .findAll()
                .stream()
                .map(player -> makeScoreDTO(player))
                .collect(toSet());
    }



    public Map<String, Object> makeShipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("shiptype", ship.getShipType());
        dto.put("location", ship.getShipLocations());
        return dto;
    }

    public Map<String, Object> makeSalvoDTO(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("turn", salvo.getTurn());
        dto.put("location", salvo.getSalvoLocations());
        return dto;
    }

    public Map<String, Object> makeGameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        Set<GamePlayer> gameplayerset = game.getGamePlayers();
        dto.put("id", game.getId());
        dto.put("created", game.getDate());
        dto.put("gameplayers", gameplayerset
                .stream()
                .map(gameplayer -> makeGamePlayerDTO(gameplayer))
                .collect(toList()));
        return dto;
    }

    public Object getUserSalvos(GamePlayer gamePlayer){
        return getSalvoes(gamePlayer.getGame().getGamePlayers().stream().filter(player -> player.getId() == gamePlayer.getId()).findFirst().orElse(null));
    }

    public Object getEnemySalvos(GamePlayer gamePlayer) {
       return getSalvoes(gamePlayer.getGame().getGamePlayers().stream().filter(player -> player.getId() != gamePlayer.getId()).findFirst().orElse(null));
    }

    public Map<String, Object> makePlayerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("username", player.getUserName());
        return dto;
    }

    public Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("created", gamePlayer.getGameDate());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
        if (gamePlayer.getScore() != null) {
            dto.put("score", gamePlayer.getScore().getScore());
        }
        return dto;
    }

    public Map<String, Object> makeScoreDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("player", player.getUserName());
        dto.put("totalscore", player.getScores().stream().map(s -> s.getScore()).mapToDouble(s -> s).sum());
        dto.put("wins", player.getScores().stream().filter(s -> s.getScore() == 1.0).count());
        dto.put("losses", player.getScores().stream().filter(s -> s.getScore() == 0.0).count());
        dto.put("ties", player.getScores().stream().filter(s -> s.getScore() == 0.5).count());
        return dto;
    }

    public Player getCurrentUser(Authentication authentication){
        return playerRepository.findByUserName(authentication.getName());
    }



    public Object getSalvoes(GamePlayer gamePlayer) {
        return gamePlayer.getSalvoes().stream()
                .map(salvo -> makeSalvoDTO(salvo))
                .collect(toList());
    }
}