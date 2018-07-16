package com.codeoftheweb.Salvo;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    public Game(Date date) {
        this.gameDate = date;
    }

    public Date getDate() {
        return this.gameDate;
    }

    public void setDate(Date date) {
        this.gameDate = date;
    }

    public List<Player> getPlayers() {
        return gamePlayers.stream().map(sub -> sub.getPlayer()).collect(toList());
    }

    public Date calculateDate(Integer seconds) {
        Instant dateToInstant = this.gameDate.toInstant();
        Instant calculatedDate = dateToInstant.plusSeconds(seconds);
        this.gameDate = Date.from(calculatedDate);

        return this.gameDate;

           }
}