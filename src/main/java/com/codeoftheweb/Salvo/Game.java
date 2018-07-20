package com.codeoftheweb.Salvo;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date gameDate;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;

    public void addGamePlayer(GamePlayer gameplayer) {
        gameplayer.setGame(this);
        gamePlayers.add(gameplayer);
    }

        public Game() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Game(Date date) {
        this.gameDate = date;
    }

    public Date getDate() {
        return this.gameDate;
    }

    public void setDate(Date date) {
        this.gameDate = date;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public void setGamePlayers(Set<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public List<Player> getPlayers() {
        return gamePlayers
                .stream()
                .map(sub -> sub.getPlayer())
                .collect(toList());
    }


    public Date calculateDate(Integer seconds) {
        Instant dateToInstant = this.gameDate.toInstant();
        Instant calculatedDate = dateToInstant.plusSeconds(seconds);
        this.gameDate = Date.from(calculatedDate);

        return this.gameDate;

           }


}