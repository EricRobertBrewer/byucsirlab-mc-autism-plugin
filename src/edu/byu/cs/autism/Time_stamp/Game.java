package edu.byu.cs.autism.Time_stamp;

import java.time.Instant;
import java.time.LocalTime;

public class Game {
    private String gameName;
    private String playerName;
    private Instant enterTime;
    private Long timeElapsed;

    public Game(String gameName, String playerName, Instant enterTime){
        this.enterTime = enterTime;
        this.gameName = gameName;
        this.playerName = playerName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Instant getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Instant enterTime) {
        this.enterTime = enterTime;
    }

    public Long getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(Long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
}
