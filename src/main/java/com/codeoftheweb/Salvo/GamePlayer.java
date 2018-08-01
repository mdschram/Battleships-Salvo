package com.codeoftheweb.Salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.jni.Local;
import org.springframework.context.annotation.ScopeMetadataResolver;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    Set<Ship> ships;

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER)
    Set<Salvo> salvoes;



    private Date gameDate;

    public GamePlayer() {
    }

    public GamePlayer(Player player, Game game, Date date) {
            this.gameDate = date;
            this.player = player;
            this.game = game;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<Ship> getShips() {
        return ships;
    }

    public void setShips(Set<Ship> ships) {
        this.ships = ships;
    }

    public Set<Salvo> getSalvoes() {
        return salvoes;
    }

    public void setSalvoes(Set<Salvo> salvoes) {
        this.salvoes = salvoes;
    }

    public Score getScore () {
        return player.getScores().stream().filter(s -> s.getGame() == game).findFirst().orElse(null);
    }



//    public Score getScore(){
//        List<Score> scoreList = new ArrayList<>();
//        Set<Score> scoreSet = player.getScores();
//        for (Score score : scoreSet) {
//            if(score.getGame() == game) {
//                scoreList.add(score);
//            }
//        }
//        Score score;
//        if (!scoreList.isEmpty()){
//            score = scoreList.get(0);
//        } else {
//            score = null;
//        }
//        return score;
//    }


}

