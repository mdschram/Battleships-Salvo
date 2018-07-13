package com.codeoftheweb.Salvo;

import org.apache.tomcat.jni.Local;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private LocalDate gameDate;

    public Game() { }

    public Game(LocalDate date) {
        this.gameDate = date;
    }

    public LocalDate getDate() {
        return this.gameDate;
    }

    public void setDate(LocalDate date) {
        this.gameDate = date;
    }
}
