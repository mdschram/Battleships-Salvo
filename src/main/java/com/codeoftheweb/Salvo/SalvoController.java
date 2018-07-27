package com.codeoftheweb.Salvo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.acl.Owner;
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
        Set<GamePlayer> gamePlayerSet = user.getGame().getGamePlayers();
        gameView.put("game", makeGameDTO(user.getGame()));
        gameView.put("ships", user.getShips()
                                  .stream()
                                  .map(thisShip -> makeShipDTO(thisShip))
                                  .collect(toList()));
        for (GamePlayer gamePlayer : gamePlayerSet) {
            if (gamePlayer.getId() != user.getId())
                {gameView.put("enemysalvoes", gamePlayer.getSalvoes()
                                                        .stream()
                                                        .map(salvo -> makeSalvoDTO(salvo))
                                                        .collect((toList())));
            }else{gameView.put("usersalvoes", user.getSalvoes()
                                                  .stream()
                                                  .map(salvo -> makeSalvoDTO(salvo))
                                                  .collect((toList())));}
        }
        return gameView;
    }

    public Map<String, Object> makeShipDTO(Ship ship) {
        Map<String, Object> dto;
        dto = new LinkedHashMap<String, Object>();
        dto.put("shiptype", ship.getShipType());
        dto.put("location", ship.getShipLocations());
        return dto;
    }

    public Map<String, Object> makeSalvoDTO(Salvo salvo) {
        Map<String, Object> dto;
        dto = new LinkedHashMap<String, Object>();
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

    public Map<String, Object> makePlayerDTO(Player player) {
            Map<String, Object> dto;
            dto = new LinkedHashMap<String, Object>();
            dto.put("id", player.getId());
            dto.put("username", player.getUserName());
            return dto;
    }

    public Map<String, Object> makeGamePlayerDTO(GamePlayer gamePlayer) {
            Map<String, Object> dto;
            dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getId());
        dto.put("created", gamePlayer.getGameDate());
        dto.put("player", makePlayerDTO(gamePlayer.getPlayer()));
        return dto;
    }
}

